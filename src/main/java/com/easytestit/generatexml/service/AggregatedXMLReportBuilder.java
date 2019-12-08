package com.easytestit.generatexml.service;

import com.easytestit.generatexml.data.XMLBuilderConstants;
import com.easytestit.generatexml.dto.input.elements.Element;
import com.easytestit.generatexml.dto.output.TemporaryTestCase;
import com.easytestit.generatexml.dto.output.TestSuite;
import com.easytestit.generatexml.dto.input.Feature;
import com.easytestit.generatexml.dto.output.AggregatedTestSuiteResult;
import com.easytestit.generatexml.dto.output.testcase.TestCase;
import com.easytestit.generatexml.dto.input.tags.Tag;
import com.easytestit.generatexml.utils.UtilsConverter;
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

//todo the first class which should be refactored
public class AggregatedXMLReportBuilder {

    private static final Logger LOGGER = LogManager.getLogger(AggregatedXMLReportBuilder.class.getName());

    private String allTagsFromAllFeatures = "";
    private String allTagsFromEachFeature = "";
    private Map<String, Object> background = new HashMap<>();
    private Map<String, Object> scenarioMap = new HashMap<>();

    private String tempTags_ = "";
    private String stepOutResults = "";
    private String stepErrResults = "";
    private int featureFilesCount = 0;
    private int countFailuresAllErrorsFromAllFiles = 0;
    private int countFailuresTestsFromAllFiles = 0;
    private int countFailuresTestFromOneFile = 0;
    private int failuresCountInSuites = 0;
    private int countScenarios = 0;
    private int successfulCountTests = 0;
    private int skippedCountTest = 0;
    private Long duration = 0L;
    private Long durationOfTest = 0L;
    private Long durationOfAllTest = 0L;

    private final String[] hostName = {""};
    private final String[] responseDate = {""};
    private final String[] scenarioTestName = {""};

    /**
     * The main goal in this method is to convert JAVA object which was deserialized from JSON file
     * to other JAVA object which will serialize to XML file
     * Here concentrate logic to calculate and handle data and put this data in DTO
     *
     * @param features prepared JAVA feature class which should be convert to another DTO
     * @return prepared DTO aggregated class with all needed data for serialize it to XML file
     */
    //TODO need to refactor this method cause here is error
    public AggregatedTestSuiteResult transformFeaturesToAggregatedReport(@NotNull Collection<Feature> features) {

        Map<Integer, TemporaryTestCase> tests = new HashMap<>();
        Collection<TestSuite> testSuites = new ArrayList<>();

        features.forEach(feature -> {
            featureFilesCount += 1;

            feature.getElements().forEach(element -> {

                if (element.getKeyword().equals(XMLBuilderConstants.SCENARIO)) countScenarios += 1;
                element.getSteps().forEach(step -> {
                    if (!step.getResult().getStatus().equals(XMLBuilderConstants.PASSED)) {
                        countFailuresTestsFromAllFiles += 1;
                        countFailuresTestFromOneFile += 1;
                    }
                    stringOutBuilder(element.getKeyword(), step.getKeyword(), step.getName(), step.getResult().getStatus());
                    durationOfTest += step.getResult().getDuration();
                });

                tests.put(element.getLine(), new TemporaryTestCase()
                        .setParentLine(element.getLine())
                        .setParentKeyword(element.getKeyword())
                        .setParentName(element.getName())
                        .setTestOutputString(stepOutResults)
                        .setTestDuration(durationOfTest));

                durationOfAllTest += durationOfTest;
                stepOutResults = "";
                durationOfTest = 0L;
            });

            testSuites.add(new TestSuite()
                    .setName(feature.getName())
                    .setErrors(String.valueOf(countFailuresTestFromOneFile))
                    .setFailures(String.valueOf(countFailuresTestFromOneFile))
                    .setId(String.valueOf(featureFilesCount))
                    .setPackageName(feature.getName())
                    .setTime(String.valueOf(durationOfAllTest))
                    .setTestCases(getTestsFromFeature(tests)));

            countScenarios = 0;
            durationOfAllTest = 0L;
            tests.clear();
        });

        return aggregateTestSuitesResult(allTagsFromAllFeatures.trim(), countFailuresAllErrorsFromAllFiles, countFailuresTestsFromAllFiles, successfulCountTests, duration, testSuites);
    }

    @NotNull
    private Collection<TestCase> getTestsFromFeature(@NotNull Map<Integer, TemporaryTestCase> tests) {
        Collection<TestCase> testCases = new ArrayList<>();

        String[] ss = {""};

        tests.values().stream().filter(t -> t.getParentKeyword().equals(XMLBuilderConstants.BACKGROUND)).forEach(tt -> {
            ss[0] = tt.getTestOutputString();
        });

        tests.values().stream().filter(t -> t.getParentKeyword().equals(XMLBuilderConstants.SCENARIO)).forEach(temporaryTestCase -> {
            testCases.add(
                    new TestCase()
                            .setTestName(temporaryTestCase.getParentName())
                            .setDescription(temporaryTestCase.getParentName())
                            .setCaseOutInfo(ss[0].concat(temporaryTestCase.getTestOutputString())));
        });

        return testCases;
    }

    /**
     * Separate method for better handling code
     * Created for getting data about tags and pass these tags to other function
     *
     * @param tags object which should be handled
     * @return string value of all tags
     */
    @NotNull
    private String fillTagsInSuitReport(@NotNull Collection<Tag> tags) {
        tags.stream().filter(
                t -> !allTagsFromEachFeature.contains(t.getName())).forEach(tag -> allTagsFromEachFeature += tag.getName().concat(" "));
        return allTagsFromEachFeature.trim();
    }

    /**
     * Helper method - which at the current time is not use
     * @param element where located whole information about Background or Scenario
     */
    private void fillBackground(@NotNull Element element) {

        final String[] stepInfo = {"", "", ""};
        if (background.get("line") == null) {

            element.getSteps().forEach(step -> {
                stepInfo[0] += step.getName();
                stepInfo[1] = step.getResult().getStatus();
                stepInfo[2] = step.getKeyword();
            });

            background.put("keyword", element.getKeyword());
            background.put("line", element.getLine());
            background.put("stepName", stepInfo[0]);
            background.put("stepStatus", stepInfo[1]);
            background.put("stepKeyword", stepInfo[2]);

        } else if (!background.get("line").equals(String.valueOf(element.getLine()))) {
            LOGGER.info("Background already created but there is only one different in duration. Could be skipped.");
        }
    }

    /**
     * Helper method - which at the current time is not use
     * @param element where located whole information about Background or Scenario
     */
    private void fillScenario(Element element) {
        final String[] stepInfo = {"", "", "", ""};
        if (scenarioMap.get("line") == null) {

            scenarioMap.put("scenarioName", element.getName());
            scenarioMap.put("scenarioKeyword", element.getKeyword());
        }
    }

    /**
     * Helper method which fill object {@link TestCase}. This object mean Test in JUnit XML report format (Background & Scenario)
     *
     * @param scenarioTestDescription - description from {@link Element} object from name field
     * @param description - description from {@link Feature} object from description field
     * @param outResults - concatenated string from all passed steps in one Test (Test is Background & Scenario)
     * @param errResults - concatenated string from all failed steps in one Test (Test is Background & Scenario)
     *
     * @return new filled and prepared {@link TestCase} object
     */
    private TestCase getTestCaseResult(String scenarioTestDescription, String description, String outResults, String errResults) {
        return new TestCase()
                .setTestName(scenarioTestDescription)
                .setDescription(description)
                .setCaseOutInfo(outResults)
                .setCaseOutErr(errResults);
    }

    /**
     * Helper method which fill object {@link TestSuite}. This object related to Feature file from Cucumber, which contains all Backgrounds and Scenarios
     *
     * @param name of feature file
     * @param size quantity of elements in one feature file
     * @param tags tags which was set upped for feature file
     * @param countInSuites quantity of errors from all scenarios and backgrounds
     * @param failuresCountInSuites quantity of failures from all scenarios and backgrounds
     * @param featureFilesCount ID of feature file which was parsed before
     * @param durationOfTest long type value of duration whole backgrounds and scenarios
     * @param hostName string value of host where request made
     * @param dateTimeISO string value of date time when request was made
     * @param testCaseResults object {@link TestCase} with data
     *
     * @return aggregated {@link TestSuite} with all {@link TestCase}
     */
    @NotNull
    private TestSuite getTestSuiteResult(
            @NotNull String name,
            int size,
            @NotNull List<Tag> tags,
            int countInSuites,
            int failuresCountInSuites,
            int featureFilesCount,
            Long durationOfTest,
            String hostName,
            String dateTimeISO,
            Collection<TestCase> testCaseResults) {
        var testSuite = new TestSuite();
        String[] names = name.split("/");
        tags.stream().filter(
                tag -> !tempTags_.contains(tag.getName())).forEach(tag -> tempTags_ += tag.getName().concat(" "));

        if (tempTags_.contains(XMLBuilderConstants.DISABLED)) {
            var j = StringUtils.countMatches(tempTags_, XMLBuilderConstants.DISABLED);
            testSuite.setDisabled(String.valueOf(j));
        }

        testSuite.setName(getLastElement(Arrays.stream(names).collect(Collectors.toList())))
                .setTests(String.valueOf(size))
                .setErrors(String.valueOf(countInSuites))
                .setFailures(String.valueOf(failuresCountInSuites))
                .setId(String.valueOf(featureFilesCount))
                .setPackageName(name)
                .setTime(String.valueOf(durationOfTest))
                .setHostname(UtilsConverter.removeRedundantSymbols.apply(hostName))
                .setTimestamp(LocalDateTime.parse(dateTimeISO, DateTimeFormatter.ofPattern(XMLBuilderConstants.DATE_FORMATTER_PATTERN, Locale.ENGLISH)).toString())
                .setTestCases(testCaseResults);

        return testSuite;
    }

    /**
     * Helper method which fill object {@link AggregatedTestSuiteResult}. This object contains all Feature file from Cucumber, which contains all Backgrounds and Scenarios
     *
     * @param tempTags string value from all features files
     * @param _countFailuresAllErrorsFromAllFiles string value of quantity of errors from all feature files
     * @param _countFailuresTestsFromAllFiles string value of quantity of failures from all feature files
     * @param successfulCountTests string value of quantity of successful tests from all feature files
     * @param _duration string value of duration for all feature files
     * @param testSuites object {@link TestSuite} with prepared data
     *
     * @return prepared object {@link AggregatedTestSuiteResult} with all prepared data
     */
    @NotNull
    private AggregatedTestSuiteResult aggregateTestSuitesResult(
            @NotNull String tempTags,
            int _countFailuresAllErrorsFromAllFiles,
            int _countFailuresTestsFromAllFiles,
            int successfulCountTests,
            Long _duration,
            Collection<TestSuite> testSuites) {
        var aggregatedTestSuits = new AggregatedTestSuiteResult();

        if (tempTags.contains(XMLBuilderConstants.IGNORED) || tempTags.contains(XMLBuilderConstants.DISABLED)) {
            var i = StringUtils.countMatches(tempTags, XMLBuilderConstants.IGNORED);
            var j = StringUtils.countMatches(tempTags, XMLBuilderConstants.DISABLED);
            aggregatedTestSuits.setDisabled(String.valueOf(i + j));
        }

        aggregatedTestSuits.setErrors(String.valueOf(_countFailuresAllErrorsFromAllFiles));
        aggregatedTestSuits.setFailures(String.valueOf(_countFailuresTestsFromAllFiles));
        aggregatedTestSuits.setTests(String.valueOf(successfulCountTests));
        aggregatedTestSuits.setTime(String.valueOf(UtilsConverter.round.apply(_duration * XMLBuilderConstants.RATIO)));
        aggregatedTestSuits.setTestSuites(testSuites);

        return aggregatedTestSuits;
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
        String[] arrayList = str.split(separator);
        return Arrays.asList(arrayList);
    }

}
