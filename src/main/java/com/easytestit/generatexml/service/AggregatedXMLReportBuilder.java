package com.easytestit.generatexml.service;

import com.easytestit.generatexml.data.DefaultData;
import com.easytestit.generatexml.dto.aggregatedreport.AggregatedTestSuite;
import com.easytestit.generatexml.dto.feature.Feature;
import com.easytestit.generatexml.dto.aggregatedreport.AggregatedTestSuiteResult;
import com.easytestit.generatexml.dto.aggregatedreport.testcase.FeatureTestCaseResult;
import com.easytestit.generatexml.dto.feature.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AggregatedXMLReportBuilder {

    private int featureFilesCount = 0;
    private Long duration = 0L;
    private Long durationOfTest = 0L;
    private String stepResults = "";
    private int failuresCountTotal = 0;
    private int failuresCountInSuites = 0;
    private int failuresCountTest = 0;
    private int skippedCountTest = 0;
    private int successfulCountTests = 0;
    private String tempTags = "";
    private String tempTags_ = "";
    private final String[] hostName = {""};
    private final String[] responseDate = {""};

    public AggregatedTestSuiteResult generateAggregatedXMLReport(@NotNull Collection<Feature> parseJSON) {
        Collection<AggregatedTestSuite> aggregatedTestSuites = new ArrayList<>(Collections.emptyList());

        Collection<FeatureTestCaseResult> testCaseResults = new ArrayList<>(Collections.emptyList());

        parseJSON.forEach(featureFile -> {
            featureFilesCount += 1;
            if (featureFile.getTags() != null)
                featureFile.getTags().stream().filter(
                        tag -> !tempTags.contains(tag.getName())).forEach(tag -> tempTags += tag.getName().concat(" "));


            featureFile.getElements().forEach(
                    element -> {
                        element.getSteps().forEach(
                                step -> {
                                    if (!step.getResult().getStatus().contains("passed")) {
                                        failuresCountTotal += 1;
                                        failuresCountInSuites += 1;
                                    } else {
                                        successfulCountTests += 1;
                                    }

                                    duration += step.getResult().getDuration();
                                    durationOfTest += step.getResult().getDuration();

                                    //TODO need refactoring
                                    var keyword = step.getKeyword();
                                    var stepName = step.getName();
                                    var stepResult = step.getResult().getStatus();

                                    stringOutBuilder(element.getKeyword(), keyword, stepName, stepResult);
                                });
                        if (element.getKeyword().equals(DefaultData.scenario)) {
                            element.getSteps().stream().filter(
                                    step -> !step.getResult().getStatus().contains("passed")).forEach(
                                            step -> failuresCountTest += 1);
                            element.getSteps().stream().filter(
                                    step -> step.getResult().getStatus().contains("skipped")).forEach(
                                            step -> skippedCountTest += 1);
                            element.getSteps().forEach(
                                    step -> {
                                        getArrayStringBySeparator(step.getDocString().getValue(), ">").forEach(
                                                request -> {
                                                    if (request.toLowerCase().contains("host")) {
                                                        hostName[0] = getArrayStringBySeparator(request, ":").get(1);
                                                    } else if (request.toLowerCase().contains("user-agent")) {
                                                        getArrayStringBySeparator(request, "<").forEach(
                                                                response -> {
                                                                    if (response.contains("Date")) {
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

            testCaseResults.add(
                    new FeatureTestCaseResult()
                            .setTestName(featureFile.getName())
                            .setDescription(featureFile.getDescription())
                            .setCaseOutInfo(stepResults)
                    );

            aggregatedTestSuites.add(
                    fillTestSuite(
                            featureFile.getName(),
                            featureFile.getElements().size(),
                            featureFile.getTags(),
                            failuresCountInSuites,
                            failuresCountTest,
                            featureFilesCount,
                            durationOfTest,
                            hostName[0],
                            responseDate[0]
                    )
            );

            failuresCountTest = 0;
            skippedCountTest = 0;
            stepResults = "";
            failuresCountInSuites = 0;
            durationOfTest = 0L;
        });

        return aggregateTestSuitesResult(tempTags, failuresCountTotal, successfulCountTests, duration, aggregatedTestSuites);
    }

    @NotNull
    private AggregatedTestSuite fillTestSuite(
            @NotNull String names,
            int size,
            @NotNull List<Tag> tags,
            int countInSuites,
            int failuresCountInSuites,
            int featureFilesCount,
            Long durationOfTest,
            String hostName,
            String dateTimeISO) {
        var aggregatedTestSuite = new AggregatedTestSuite();
        String[] name = names.split("/");

        aggregatedTestSuite.setName(getLastElement(Arrays.stream(name).collect(Collectors.toList())));
        aggregatedTestSuite.setTests(String.valueOf(size));

        tags.stream().filter(
                tag -> !tempTags_.contains(tag.getName())).forEach(tag -> tempTags_ += tag.getName().concat(" "));

        if (tempTags_.contains("disabled")) {
            var j = StringUtils.countMatches(tempTags_, "disabled");
            aggregatedTestSuite.setDisabled(String.valueOf(j));
        }



        aggregatedTestSuite.setErrors(String.valueOf(countInSuites));
        aggregatedTestSuite.setFailures(String.valueOf(failuresCountInSuites));
        aggregatedTestSuite.setId(String.valueOf(featureFilesCount));
        aggregatedTestSuite.setPackageName(names);
        aggregatedTestSuite.setTime(String.valueOf(durationOfTest));
        aggregatedTestSuite.setHostname(hostName.replace("\\n1",""));
        aggregatedTestSuite.setTimestamp(LocalDateTime.parse(dateTimeISO, DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH)).toString());

        return aggregatedTestSuite;
    }

    @NotNull
    private AggregatedTestSuiteResult aggregateTestSuitesResult(@NotNull String tempTags, int failuresCountTotal, int successfulCountTests, Long duration, Collection<AggregatedTestSuite> aggregatedTestSuites) {
        var aggregatedTestSuits = new AggregatedTestSuiteResult();

        if (tempTags.contains("ignore") || tempTags.contains("disabled")) {
            var i = StringUtils.countMatches(tempTags, "ignore");
            var j = StringUtils.countMatches(tempTags, "disabled");
            aggregatedTestSuits.setDisabled(String.valueOf(i + j));
        }

        aggregatedTestSuits.setErrors(String.valueOf(failuresCountTotal));
        aggregatedTestSuits.setFailures(String.valueOf(failuresCountTotal));
        aggregatedTestSuits.setTests(String.valueOf(successfulCountTests));
        aggregatedTestSuits.setTime(String.valueOf(duration));
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

        stepResults += "\n".concat(outString.toString());
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
