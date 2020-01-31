package com.easytestit.generatexml.service;

import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.data.XMLBuilderConstants;
import com.easytestit.generatexml.dto.input.elements.Element;
import com.easytestit.generatexml.dto.input.elements.steps.Step;
import com.easytestit.generatexml.dto.output.SingleReportSuite;
import com.easytestit.generatexml.dto.input.Feature;
import com.easytestit.generatexml.dto.output.XMLReport;
import com.easytestit.generatexml.dto.output.testcase.TestCase;
import com.easytestit.generatexml.dto.input.tags.Tag;
import com.easytestit.generatexml.utils.UtilsConverter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    public ReportService() {

    }

    private Map<Integer, TemporaryTestCase> tests = new LinkedHashMap<>();
    private Map<String, String> featureTagsMap = new HashMap<>();
    private String featureTags = "";
    private String tagsValue = "";
    private String hostName = "";
    private String responseDate = "";
    private String stepOutResults = "";
    private String stepErrResults = "";
    private String backgroundValue = "";
    private String testCaseStatus = "";
    private int countFailures = 0;
    private int countFailuresTestFromOneFile = 0;
    private int countSkippedTestFromOneFile = 0;
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
    public XMLReport transformFeaturesToReport(final Collection<Feature> features) {
        if (features != null) {
            Collection<SingleReportSuite> singleReportSuites = new ArrayList<>();

            if (features.isEmpty()) {
                throw new GenerateXMLReportException("Empty argument provided");
            }

            features.forEach(feature -> {
                if (feature != null) {
                    if (feature.getTags() != null) tagProcessing("featureTags", feature.getTags());
                    if (feature.getElements() != null)
                        feature.getElements().forEach(element -> elementProcessing(feature, element));

                    singleReportSuites.add(getSingleReportSuite(feature, features));
                    durationOfAllTestFromAllSuites += durationOfAllTest;
                    resetVariables();
                } else {
                    throw new GenerateXMLReportException("Argument feature from the Array features should not be null but is null");
                }
            });

            return getReportSuites(countFailures, countScenarios, durationOfAllTestFromAllSuites, singleReportSuites);
        } else {
            throw new GenerateXMLReportException("Argument features should not be null but is null. See detailed stack trace: ", new NullPointerException());
        }
    }

    /**
     * Separate method for better handling code
     * Created for getting data about tags and pass these tags to other function
     *
     * @param tagKey the key value for processing tags for specific block of feature file
     * @param tags   object which should be handled
     */
    private void tagProcessing(final String tagKey, final Collection<Tag> tags) {
        if (tags != null & tagKey != null) {
            tags.stream()
                    .filter(t -> !featureTags.contains(t.getName()))
                    .forEach(t -> tagsValue += t.getName().concat(" "));
            if (!tagsValue.isEmpty()) featureTagsMap.put(tagKey, tagsValue);
            tagsValue = "";
        } else {
            throw new GenerateXMLReportException("Arguments tags, tagKey should not be null but are null. See detailed stack trace: ", new NullPointerException());
        }
    }

    private void elementProcessing(final Feature feature, final Element element) {
        if (feature != null & element != null) {

            if (element.getTags() != null) tagProcessing("elementTags", element.getTags());
            if (element.getKeyword().equals(XMLBuilderConstants.SCENARIO)) {
                countScenarios += 1;
                countScenariosInSuite += 1;
            }
            if (element.getSteps() != null)
                element.getSteps().forEach(step -> stepProcessing(element, step));

            var tagStrValues = "";
            if (!(featureTagsMap.get("elementTags") == null)) tagStrValues += featureTagsMap.get("elementTags");
            if (!(featureTagsMap.get("featureTags") == null)) tagStrValues += featureTagsMap.get("featureTags");

            tests.put(element.getLine(), new TemporaryTestCase()
                    .setLine(element.getLine())
                    .setStatus(testCaseStatus.contains(XMLBuilderConstants.FAILED) ? XMLBuilderConstants.FAILED : XMLBuilderConstants.PASSED)
                    .setKeyword(element.getKeyword())
                    .setName(element.getName())
                    .setDescription(feature.getDescription())
                    .setTestOutputString(stepOutResults)
                    .setTestErrorOutputString("Stack Trace: \n".concat(stepErrResults))
                    .setTestDuration(durationOfTest)
                    .setTags(tagStrValues));

            durationOfAllTest += durationOfTest;
            stepOutResults = "";
            stepErrResults = "";
            testCaseStatus = "";
            durationOfTest = 0L;

            featureTagsMap.remove("elementTags");
        } else {
            throw new GenerateXMLReportException("Arguments feature & element should not be null but are null. See detailed stack trace: ", new NullPointerException());
        }
    }

    private SingleReportSuite getSingleReportSuite(final Feature feature, final Collection<Feature> features) {
        var increment = 1;
        var ignored = 0;
        var disabled = 0;
        Collection<TestCase> testCases = new ArrayList<>();

        if (featureTags.contains(XMLBuilderConstants.IGNORED) || featureTags.contains(XMLBuilderConstants.DISABLED)) {
            ignored = StringUtils.countMatches(featureTags, XMLBuilderConstants.IGNORED);
            disabled = StringUtils.countMatches(featureTags, XMLBuilderConstants.DISABLED);
        }
        if (tests != null) {
            testCases = getTestCasesFromFeature(tests);
        } else {
            throw new GenerateXMLReportException("Map of tests should not be null but is null. See detailed stack trace: ", new NullPointerException());
        }

        return new SingleReportSuite()
                .setName(getLastElement(Arrays.stream(feature.getName().split(XMLBuilderConstants.SLASH)).collect(Collectors.toList())))
                .setTests(String.valueOf(countScenariosInSuite))
                .setFailures(String.valueOf(countFailuresTestFromOneFile))
                .setDisabled(String.valueOf(ignored + disabled))
                .setHostname(hostName)
                .setId(String.valueOf(((ArrayList) features).indexOf(feature) + increment))
                .setPackageName(feature.getName())
                .setSkipped(String.valueOf(countSkippedTestFromOneFile))
                .setTime(String.valueOf(UtilsConverter.round.apply(durationOfAllTest * XMLBuilderConstants.RATIO)))
                .setTimestamp(LocalDateTime.parse(responseDate, DateTimeFormatter.ofPattern(XMLBuilderConstants.DATE_FORMATTER_PATTERN, Locale.ENGLISH)).toString())
                .setTags(featureTagsMap.get("featureTags"))
                .setTestCases(testCases);
    }

    private void resetVariables() {
        durationOfAllTest = 0L;
        countScenariosInSuite = 0;
        countFailuresTestFromOneFile = 0;
        countSkippedTestFromOneFile = 0;
        featureTags = "";

        featureTagsMap.clear();
        tests.clear();
    }

    private void stepProcessing(final Element element, final Step step) {
        if (element != null & step != null) {
            switch (step.getResult().getStatus()) {
                case XMLBuilderConstants.FAILED:
                    countFailures += 1;
                    countFailuresTestFromOneFile += 1;
                    if (!testCaseStatus.contains(XMLBuilderConstants.FAILED))
                        testCaseStatus += step.getResult().getStatus().concat(" ");
                    stringErrBuilder(step.getResult().getError_message());
                    break;
                case XMLBuilderConstants.SKIPPED:
                    countSkippedTestFromOneFile += 1;
                    break;
                case XMLBuilderConstants.PASSED:
                    if (!testCaseStatus.contains(XMLBuilderConstants.PASSED))
                        testCaseStatus += step.getResult().getStatus().concat(" ");
                    break;
            }

            stringOutBuilder(element.getKeyword(), step.getKeyword(), step.getName(), step.getResult().getStatus());
            durationOfTest += step.getResult().getDuration();

            if (step.getDoc_string() != null) {
                if (step.getDoc_string().getValue() != null) {
                    getArrayStringBySeparator(step.getDoc_string().getValue(), ">").forEach(
                            request -> {
                                if (request != null) {
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
                                } else {
                                    throw new GenerateXMLReportException("Argument request from Array should be null but is null. See detailed stack trace: ", new NullPointerException());
                                }
                            }
                    );
                } else {
                    throw new GenerateXMLReportException("Argument value from doc_String element of steps should not be null but is null. Please check your JSON.");
                }
            }
        } else {
            throw new GenerateXMLReportException("Arguments element & step should not be null but are null. See detailed stack trace: ", new NullPointerException());
        }
    }

    private Collection<TestCase> getTestCasesFromFeature(final Map<Integer, TemporaryTestCase> tests) {
        Collection<TestCase> testCases = new ArrayList<>();

        tests.values().stream()
                .filter(t -> t.getKeyword().equals(XMLBuilderConstants.BACKGROUND))
                .forEach(t -> backgroundValue = t.getTestOutputString());//TODO what?
        tests.values().stream()
                .filter(t -> t.getKeyword().equals(XMLBuilderConstants.SCENARIO))
                .forEach(t -> testCases.add(
                        new TestCase()
                                .setStatus(t.getStatus())
                                .setTestName(t.getName())
                                .setDescription(t.getDescription())
                                .setCaseOutInfo(backgroundValue.concat(t.getTestOutputString()))
                                .setCaseOutErr(t.getTestErrorOutputString())));

        if (testCases != null) {
            return testCases;
        } else {
            throw new GenerateXMLReportException("Argument testCases should be null but is null. See detailed stack trace: ", new NullPointerException());
        }
    }

    /**
     * Helper method which fill object {@link XMLReport}.
     * This object contains all Feature file from Cucumber, which contains all Backgrounds and Scenarios
     *
     * @param countFailuresTestsFromAllFiles string value of quantity of failures from all feature files
     * @param successfulCountTests           string value of quantity of successful tests from all feature files
     * @param duration                       string value of duration for all feature files
     * @param singleReportSuites             object {@link SingleReportSuite} with prepared data
     * @return prepared object {@link XMLReport} with all prepared data
     */
    private XMLReport getReportSuites(
            final int countFailuresTestsFromAllFiles,
            final int successfulCountTests,
            final Long duration,
            final Collection<SingleReportSuite> singleReportSuites
    ) {
        var xmlReport = new XMLReport();
        xmlReport
                .setFailures(String.valueOf(countFailuresTestsFromAllFiles))
                .setTests(String.valueOf(successfulCountTests))
                .setTime(String.valueOf(UtilsConverter.round.apply(duration * XMLBuilderConstants.RATIO)))
                .setSingleReportSuites(singleReportSuites);

        return xmlReport;
    }

    /**
     * Helper method that processes the input string and accumulates its value to other string field of class
     *
     * @param keywordType string value keyword of Element
     * @param keyword     string value keyword of Step
     * @param stepName    string value name of Step
     * @param stepResult  string value result of Step
     */
    @Contract(pure = true)
    private void stringOutBuilder(
            final String keywordType,
            final String keyword,
            final String stepName,
            final String stepResult
    ) {
        var outLength = 15;
        var outLengthSecond = 180;
        var outString = new StringBuilder(keywordType);

        while (outString.length() < outLength) outString.append(".");
        outString.append(keyword.concat(" ").concat(stepName));
        while (outString.length() < outLengthSecond) outString.append(".");
        outString.append(stepResult);

        stepOutResults += "\n".concat(outString.toString());
    }

    /**
     * Helper method that processes the input string and accumulates its value to other string field of class
     *
     * @param errMessage string value that should be accumulated
     */
    @Contract(pure = true)
    private void stringErrBuilder(final String errMessage) {
        stepErrResults += "\n".concat(errMessage);
    }

    /**
     * Functional method which get the last element from collection
     *
     * @param elements iterable object which should processed
     * @return T the type of elements returned by the iterator
     */
    @Contract(pure = true)
    private <T> T getLastElement(final Iterable<T> elements) {
        T lastElement = null;

        for (T element : elements) {
            lastElement = element;
        }

        return lastElement;
    }

    /**
     * Helper converter string to array after splitting by specific separator symbol
     *
     * @param str       which should be split and should be convert
     * @param separator string value of separator for split the string
     * @return string arrays
     */
    private List<String> getArrayStringBySeparator(final String str, final String separator) {
        if (str != null & separator != null) {
            try {
                return Arrays.asList(str.split(separator));
            } catch (NullPointerException e) {
                throw new GenerateXMLReportException(e);
            }
        } else {
            throw new GenerateXMLReportException("Arguments str, separator should not be null but are null. See detailed stack trace: ", new NullPointerException());
        }
    }

    static class TemporaryTestCase {
        private String keyword;
        private String status;
        private Integer line;
        private String name;
        private String description;
        private Long testDuration;
        private String tags;
        private String testOutputString;
        private String testErrorOutputString;

        public TemporaryTestCase setKeyword(final String keyword) {
            this.keyword = keyword;
            return this;
        }

        public TemporaryTestCase setStatus(final String status) {
            this.status = status;
            return this;
        }

        public TemporaryTestCase setLine(final Integer line) {
            this.line = line;
            return this;
        }

        public TemporaryTestCase setName(final String name) {
            this.name = name;
            return this;
        }

        public TemporaryTestCase setTestDuration(final Long testDuration) {
            this.testDuration = testDuration;
            return this;
        }

        public TemporaryTestCase setTags(final String tags) {
            this.tags = tags;
            return this;
        }

        public TemporaryTestCase setTestOutputString(final String testOutputString) {
            this.testOutputString = testOutputString;
            return this;
        }

        public TemporaryTestCase setDescription(final String description) {
            this.description = description;
            return this;
        }

        public TemporaryTestCase setTestErrorOutputString(final String testErrorOutputString) {
            this.testErrorOutputString = testErrorOutputString;
            return this;
        }

        public String getKeyword() {
            return keyword;
        }

        public String getStatus() {
            return status;
        }

        public Integer getLine() {
            return line;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Long getTestDuration() {
            return testDuration;
        }

        public String getTags() {
            return tags;
        }

        public String getTestOutputString() {
            return testOutputString;
        }

        public String getTestErrorOutputString() {
            return testErrorOutputString;
        }
    }
}
