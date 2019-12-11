package com.easytestit.generatexml.service;

import com.easytestit.generatexml.data.XMLBuilderConstants;
import com.easytestit.generatexml.dto.input.elements.Element;
import com.easytestit.generatexml.dto.input.elements.steps.Step;
import com.easytestit.generatexml.dto.output.SingleReportSuite;
import com.easytestit.generatexml.dto.input.Feature;
import com.easytestit.generatexml.dto.output.XMLReport;
import com.easytestit.generatexml.dto.output.testcase.TestCase;
import com.easytestit.generatexml.dto.input.tags.Tag;
import com.easytestit.generatexml.utils.UtilsConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TransformService {

    private static final Logger LOGGER = LogManager.getLogger(TransformService.class.getName());

    private Map<Integer, TemporaryTestCase> tests = new HashMap<>();
    private String allTagsFromAllFeatures = "";
    private String hostName = "";
    private String responseDate = "";
    private String stepOutResults = "";
    private String stepErrResults = "";
    private String backgroundValue = "";
    private int featureFilesCount = 0;
    private int countFailuresTestsFromAllFiles = 0;
    private int countFailuresTestFromOneFile = 0;
    private int countScenarios = 0;
    private int countScenariosInSuite = 0;
    private Long durationOfTest = 0L;
    private Long durationOfAllTest = 0L;
    private Long durationOfAllTestFromAllSuites = 0L;

    /**
     * The main goal in this method is to convert JAVA object which was deserialized from JSON file
     * to other JAVA object which will serialize to XML file
     * Here concentrate logic to calculate and handle data and put this data in DTO
     *
     * @param features prepared JAVA feature class which should be convert to another DTO
     * @return prepared DTO aggregated class with all needed data for serialize it to XML file
     */
    public XMLReport transformFeaturesToReport(@NotNull Collection<Feature> features) {
        LOGGER.info("Method transformFeaturesToReportSuites invoked");
        Collection<SingleReportSuite> singleReportSuites = new ArrayList<>();

        features.forEach(feature -> {
            featureFilesCount += 1;
            if (feature.getTags() != null) tagsProcessing(feature.getTags());
            if (feature.getElements() != null)
                feature.getElements().forEach(element -> elementProcessing(feature, element));

            singleReportSuites.add(new SingleReportSuite()
                    .setTests(String.valueOf(countScenariosInSuite))
                    .setName(getLastElement(Arrays.stream(feature.getName().split("/")).collect(Collectors.toList())))
                    .setErrors(String.valueOf(countFailuresTestFromOneFile))
                    .setFailures(String.valueOf(countFailuresTestFromOneFile))
                    .setId(String.valueOf(featureFilesCount))
                    .setPackageName(feature.getName())
                    .setTime(String.valueOf(UtilsConverter.round.apply(durationOfAllTest * XMLBuilderConstants.RATIO)))
                    .setTimestamp(LocalDateTime.parse(responseDate, DateTimeFormatter.ofPattern(XMLBuilderConstants.DATE_FORMATTER_PATTERN, Locale.ENGLISH)).toString())
                    .setTestCases(getTestCasesFromFeature(tests))
                    .setHostname(hostName));

            durationOfAllTestFromAllSuites += durationOfAllTest;
            durationOfAllTest = 0L;
            countScenariosInSuite = 0;
            countFailuresTestFromOneFile = 0;

            tests.clear();
        });

        return getReportSuites(allTagsFromAllFeatures.trim(), countFailuresTestsFromAllFiles, countFailuresTestsFromAllFiles, countScenarios, durationOfAllTestFromAllSuites, singleReportSuites);
    }

    private void elementProcessing(Feature feature, @NotNull Element element) {
        if (element.getKeyword().equals(XMLBuilderConstants.SCENARIO)) {
            countScenarios += 1;
            countScenariosInSuite += 1;
        }
        if (element.getTags() != null) tagsProcessing(element.getTags());
        if (element.getSteps() != null)
            element.getSteps().forEach(step -> stepProcessing(element, step));

        tests.put(element.getLine(), new TemporaryTestCase()
                .setLine(element.getLine())
                .setKeyword(element.getKeyword())
                .setName(element.getName())
                .setDescription(feature.getDescription())
                .setTestOutputString(stepOutResults)
                .setTestErrorOutputString(stepErrResults)
                .setTestDuration(durationOfTest));

        durationOfAllTest += durationOfTest;
        stepOutResults = "";
        durationOfTest = 0L;
    }

    private void stepProcessing(Element element, @NotNull Step step) {
        if (!step.getResult().getStatus().equals(XMLBuilderConstants.PASSED)) {
            countFailuresTestsFromAllFiles += 1;
            countFailuresTestFromOneFile += 1;
            stringErrBuilder(step.getResult().getErrorMessage());
        } else if (step.getResult().getStatus().equals(XMLBuilderConstants.PASSED)) {
            stringOutBuilder(element.getKeyword(), step.getKeyword(), step.getName(), step.getResult().getStatus());
        }
        durationOfTest += step.getResult().getDuration();

        if (step.getDocString() != null)
            getArrayStringBySeparator(step.getDocString().getValue(), ">").forEach(
                    request -> {
                        if (request.toLowerCase().contains(XMLBuilderConstants.HOST)) {
                            hostName = UtilsConverter.removeRedundantSymbols.apply(getArrayStringBySeparator(request, ":").get(1));
                        } else if (request.toLowerCase().contains(XMLBuilderConstants.USER_AGENT)) {
                            getArrayStringBySeparator(request, "<").forEach(
                                    response -> {
                                        if (response.contains(XMLBuilderConstants.DATE_TEXT)) {
                                            responseDate = response.substring(7, 32);
                                        }
                                    }
                            );
                        }
                    }
            );
    }

    @NotNull
    private Collection<TestCase> getTestCasesFromFeature(@NotNull Map<Integer, TemporaryTestCase> tests) {
        Collection<TestCase> testCases = new ArrayList<>();

        tests.values().stream().filter(testCase -> testCase.getKeyword().equals(XMLBuilderConstants.BACKGROUND)).forEach(tt -> backgroundValue = tt.getTestOutputString());
        tests.values().stream().filter(t -> t.getKeyword().equals(XMLBuilderConstants.SCENARIO)).forEach(temporaryTestCase -> testCases.add(
                new TestCase()
                        .setTestName(temporaryTestCase.getName())
                        .setDescription(temporaryTestCase.getDescription())
                        .setCaseOutInfo(backgroundValue.concat(temporaryTestCase.getTestOutputString()))
                        .setCaseOutErr(temporaryTestCase.getTestErrorOutputString())));

        return testCases;
    }

    /**
     * Separate method for better handling code
     * Created for getting data about tags and pass these tags to other function
     *
     * @param tags object which should be handled
     */
    private void tagsProcessing(@NotNull Collection<Tag> tags) {
        tags.stream().filter(
                t -> !allTagsFromAllFeatures.contains(t.getName())).forEach(tag -> allTagsFromAllFeatures += tag.getName().concat(" "));
    }

    /**
     * Helper method which fill object {@link XMLReport}. This object contains all Feature file from Cucumber, which contains all Backgrounds and Scenarios
     *
     * @param tempTags string value from all features files
     * @param _countFailuresAllErrorsFromAllFiles string value of quantity of errors from all feature files
     * @param _countFailuresTestsFromAllFiles string value of quantity of failures from all feature files
     * @param successfulCountTests string value of quantity of successful tests from all feature files
     * @param _duration string value of duration for all feature files
     * @param singleReportSuites object {@link SingleReportSuite} with prepared data
     *
     * @return prepared object {@link XMLReport} with all prepared data
     */
    @NotNull
    private XMLReport getReportSuites(
            @NotNull String tempTags,
            int _countFailuresAllErrorsFromAllFiles,
            int _countFailuresTestsFromAllFiles,
            int successfulCountTests,
            Long _duration,
            Collection<SingleReportSuite> singleReportSuites) {
        var reportSuites = new XMLReport();

        if (tempTags.contains(XMLBuilderConstants.IGNORED) || tempTags.contains(XMLBuilderConstants.DISABLED)) {
            var i = StringUtils.countMatches(tempTags, XMLBuilderConstants.IGNORED);
            var j = StringUtils.countMatches(tempTags, XMLBuilderConstants.DISABLED);
            reportSuites.setDisabled(String.valueOf(i + j));
        }

        reportSuites.setErrors(String.valueOf(_countFailuresAllErrorsFromAllFiles))
                .setFailures(String.valueOf(_countFailuresTestsFromAllFiles))
                .setTests(String.valueOf(successfulCountTests))
                .setTime(String.valueOf(UtilsConverter.round.apply(_duration * XMLBuilderConstants.RATIO)))
                .setSingleReportSuites(singleReportSuites)
                .setTags(tempTags);

        return reportSuites;
    }

    /**
     * Helper method that processes the input string and accumulates its value to other string field of class
     *
     * @param keywordType string value keyword of Element
     * @param keyword string value keyword of Step
     * @param stepName string value name of Step
     * @param stepResult_ string value result of Step
     */
    @Contract(pure = true)
    private void stringOutBuilder(String keywordType, String keyword, String stepName, String stepResult_) {
        var outLength = 15;
        var outLengthSecond = 90;
        var outString = new StringBuilder(keywordType);

        while (outString.length() < outLength) outString.append(".");
        outString.append(keyword.concat(" ").concat(stepName));
        while (outString.length() < outLengthSecond) outString.append(".");
        outString.append(stepResult_);

        stepOutResults += "\n".concat(outString.toString());
    }

    /**
     * Helper method that processes the input string and accumulates its value to other string field of class
     *
     * @param errMessage string value that should be accumulated
     */
    @Contract(pure = true)
    private void stringErrBuilder(String errMessage) {
        stepErrResults += "\n".concat(errMessage);
    }

    /**
     * Functional method which get the last element from collection
     *
     * @param elements iterable object which should processed
     * @param <T> the type of elements returned by the iterator
     * @return
     */
    @Contract(pure = true)
    private <T> T getLastElement(@NotNull final Iterable<T> elements) {
        T lastElement = null;

        for (T element : elements) {
            lastElement = element;
        }

        return lastElement;
    }

    /**
     * Helper converter string to array after splitting by specific separator symbol
     *
     * @param str which should be split and should be convert
     * @param separator string value of separator for split the string
     *
     * @return string arrays
     */
    @NotNull
    private List<String> getArrayStringBySeparator(@NotNull String str, String separator) {
        return Arrays.asList(str.split(separator));
    }

    @NoArgsConstructor
    @Getter
    static class TemporaryTestCase {

        private String keyword;
        private Integer line;
        private String name;
        private String description;
        private Long testDuration;
        private String testOutputString;
        private String testErrorOutputString;

        public TemporaryTestCase setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public TemporaryTestCase setLine(Integer line) {
            this.line = line;
            return this;
        }

        public TemporaryTestCase setName(String name) {
            this.name = name;
            return this;
        }

        public TemporaryTestCase setTestDuration(Long testDuration) {
            this.testDuration = testDuration;
            return this;
        }

        public TemporaryTestCase setTestOutputString(String testOutputString) {
            this.testOutputString = testOutputString;
            return this;
        }

        public TemporaryTestCase setDescription(String description) {
            this.description = description;
            return this;
        }

        public TemporaryTestCase setTestErrorOutputString(String testErrorOutputString) {
            this.testErrorOutputString = testErrorOutputString;
            return this;
        }
    }

}
