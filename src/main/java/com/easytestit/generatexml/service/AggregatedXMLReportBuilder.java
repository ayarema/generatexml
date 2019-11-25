package com.easytestit.generatexml.service;

import com.easytestit.generatexml.data.DefaultData;
import com.easytestit.generatexml.data.XMLBuilderConstants;
import com.easytestit.generatexml.dto.output.AggregatedTestSuite;
import com.easytestit.generatexml.dto.input.Feature;
import com.easytestit.generatexml.dto.output.AggregatedTestSuiteResult;
import com.easytestit.generatexml.dto.output.testcase.AggregatedTestCase;
import com.easytestit.generatexml.dto.input.tags.Tag;
import com.easytestit.generatexml.utils.UtilsConverter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AggregatedXMLReportBuilder {

    private String allTagsFromAllFeatures = "";
    private String allTagsFromEachFeature = "";
    private Integer elementLine = 0;

    private String tempTags = "";
    private String tempTags_ = "";
    private String stepOutResults = "";
    private String stepErrResults = "";
    private int featureFilesCount = 0;
    private int countFailuresAllErrorsFromAllFiles = 0;
    private int countFailuresTestsFromAllFiles = 0;
    private int countFailuresTestFromOneFile = 0;
    private int failuresCountInSuites = 0;
    private int successfulCountTests = 0;
    private int skippedCountTest = 0;
    private Long duration = 0L;
    private Long durationOfTest = 0L;

    private final String[] hostName = {""};
    private final String[] responseDate = {""};
    private final String[] scenarioTestName = {""};

    public AggregatedTestSuiteResult generateAggregatedXMLReport(@NotNull Collection<Feature> parseJSON) {
        Collection<AggregatedTestSuite> aggregatedTestSuites = new ArrayList<>(Collections.emptyList());
        Collection<AggregatedTestCase> aggregatedTestCase = new ArrayList<>(Collections.emptyList());

        parseJSON.forEach(featureFile -> {
            featureFilesCount += 1;

            if (featureFile.getTags() != null) allTagsFromAllFeatures += fillTagsInSuitReport(featureFile.getTags());

            if (featureFile.getElements() != null) {
                featureFile.getElements().forEach(
                        element -> {
                            if (element.getKeyword().equals(DefaultData.background)) {
                                elementLine = element.getLine();
                            }
                            if (element.getSteps() != null) {
                                element.getSteps().forEach(
                                        step -> {
                                            var keyword = step.getKeyword();
                                            var stepName = step.getName();
                                            var stepResult = step.getResult().getStatus();

                                            if (!step.getResult().getStatus().contains(XMLBuilderConstants.PASSED)) {
                                                countFailuresAllErrorsFromAllFiles += 1;
                                                failuresCountInSuites += 1;
                                                stringErrBuilder(step.getResult().getErrorMessage());
                                            } else {
                                                successfulCountTests += 1;
                                                stringOutBuilder(element.getKeyword(), keyword, stepName, stepResult);
                                            }

                                            duration += step.getResult().getDuration();
                                            durationOfTest += step.getResult().getDuration();
                                        }
                                );

                            }
                            if (element.getKeyword().equals(DefaultData.scenario)) {
                                scenarioTestName[0] = element.getName();
                                element.getSteps().stream().filter(
                                        step -> !step.getResult().getStatus().contains(XMLBuilderConstants.PASSED)).forEach(
                                        step -> {
                                            countFailuresTestFromOneFile += 1;
                                            countFailuresTestsFromAllFiles += 1;
                                        });
                                element.getSteps().stream().filter(
                                        step -> step.getResult().getStatus().contains(XMLBuilderConstants.SKIPPED)).forEach(
                                        step -> skippedCountTest += 1);
                                element.getSteps().forEach(
                                        step -> {
                                            if (step.getDocString() != null)
                                                getArrayStringBySeparator(step.getDocString().getValue(), ">").forEach(
                                                        request -> {
                                                            if (request.toLowerCase().contains(XMLBuilderConstants.HOST)) {
                                                                hostName[0] = getArrayStringBySeparator(request, ":").get(1);
                                                            } else if (request.toLowerCase().contains(XMLBuilderConstants.USER_AGENT)) {
                                                                getArrayStringBySeparator(request, "<").forEach(
                                                                        response -> {
                                                                            if (response.contains(XMLBuilderConstants.DATE_TEXT)) {
                                                                                responseDate[0] = response.substring(7, 32);
                                                                            }
                                                                        }
                                                                );
                                                            }
                                                        }
                                                );
                                        }
                                );
                            }
                        }
                );
            }

            aggregatedTestCase.add(getTestCaseResult(scenarioTestName[0], featureFile.getDescription(), stepOutResults, stepErrResults));

            aggregatedTestSuites.add(
                    getTestSuiteResult(
                            featureFile.getName(),
                            featureFile.getElements().size(),
                            featureFile.getTags(),
                            failuresCountInSuites,
                            countFailuresTestFromOneFile,
                            featureFilesCount,
                            durationOfTest,
                            hostName[0],
                            responseDate[0],
                            aggregatedTestCase
                    )
            );

            failuresCountInSuites = skippedCountTest = countFailuresTestFromOneFile = 0;
            stepOutResults = "";
            durationOfTest = 0L;

            allTagsFromEachFeature = "";
        });

        featureFilesCount = 0;

        return aggregateTestSuitesResult(allTagsFromAllFeatures.trim(), countFailuresAllErrorsFromAllFiles, countFailuresTestsFromAllFiles, successfulCountTests, duration, aggregatedTestSuites);
    }

    @NotNull
    private String fillTagsInSuitReport(@NotNull Collection<Tag> tags) {
        tags.stream().filter(
                t -> !allTagsFromEachFeature.contains(t.getName())).forEach(tag -> allTagsFromEachFeature += tag.getName().concat(" "));
        return allTagsFromEachFeature.trim();
    }

    private AggregatedTestCase getTestCaseResult(String scenarioTestDescription, String description, String outResults, String errResults) {
        return new AggregatedTestCase()
                .setTestName(scenarioTestDescription)
                .setDescription(description)
                .setCaseOutInfo(outResults)
                .setCaseOutErr(errResults);
    }

    private void collectFeatureTags(String featureTagName) {
        tempTags += featureTagName;
    }

    @NotNull
    private AggregatedTestSuite getTestSuiteResult(
            @NotNull String names,
            int size,
            @NotNull List<Tag> tags,
            int countInSuites,
            int failuresCountInSuites,
            int featureFilesCount,
            Long durationOfTest,
            String hostName,
            String dateTimeISO,
            Collection<AggregatedTestCase> testCaseResults) {
        var aggregatedTestSuite = new AggregatedTestSuite();
        String[] name = names.split("/");
        tags.stream().filter(
                tag -> !tempTags_.contains(tag.getName())).forEach(tag -> tempTags_ += tag.getName().concat(" "));

        if (tempTags_.contains(XMLBuilderConstants.DISABLED)) {
            var j = StringUtils.countMatches(tempTags_, XMLBuilderConstants.DISABLED);
            aggregatedTestSuite.setDisabled(String.valueOf(j));
        }

        aggregatedTestSuite.setName(getLastElement(Arrays.stream(name).collect(Collectors.toList())))
                .setTests(String.valueOf(size))
                .setErrors(String.valueOf(countInSuites))
                .setFailures(String.valueOf(failuresCountInSuites))
                .setId(String.valueOf(featureFilesCount))
                .setPackageName(names)
                .setTime(String.valueOf(durationOfTest))
                .setHostname(UtilsConverter.removeRedundantSymbols.apply(hostName))
                .setTimestamp(LocalDateTime.parse(dateTimeISO, DateTimeFormatter.ofPattern(XMLBuilderConstants.DATE_FORMATTER_PATTERN, Locale.ENGLISH)).toString())
                .setTestCases(testCaseResults);

        return aggregatedTestSuite;
    }

    @NotNull
    private AggregatedTestSuiteResult aggregateTestSuitesResult(
            @NotNull String tempTags,
            int _countFailuresAllErrorsFromAllFiles,
            int _countFailuresTestsFromAllFiles,
            int successfulCountTests,
            Long _duration,
            Collection<AggregatedTestSuite> aggregatedTestSuites) {
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
        aggregatedTestSuits.setTestSuites(aggregatedTestSuites);

        return aggregatedTestSuits;
    }

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

    @Contract(pure = true)
    private void stringErrBuilder(String errMessage) {
        stepErrResults += "\n".concat(errMessage);
    }

    @Contract(pure = true)
    private <T> T getLastElement(@NotNull final Iterable<T> elements) {
        T lastElement = null;

        for (T element : elements) {
            lastElement = element;
        }

        return lastElement;
    }

    @NotNull
    private List<String> getArrayStringBySeparator(@NotNull String str, String separator) {
        String[] arrayList = str.split(separator);
        return Arrays.asList(arrayList);
    }

}
