package com.easytestit.generatexml.service;

import com.easytestit.generatexml.dto.feature.Feature;
import com.easytestit.generatexml.dto.result.FeatureResult;
import com.easytestit.generatexml.dto.result.testcase.FeatureTestCaseResult;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ResultBuilder {

    private Long duration = 0L;
    private String stepResults = "";
    private int failuresCount;
    private String tempTags = "";

    public FeatureResult generateAggregatedReport(@NotNull Collection<Feature> parseJSON) {
        var featureResult = new FeatureResult();
        Collection<FeatureTestCaseResult> testCaseResults = new ArrayList<>(Collections.emptyList());

        parseJSON.forEach(featureFile -> {
            featureFile.getTags().stream().filter(tag -> !tempTags.contains(tag.getName())).forEach(tag -> tempTags += tag.getName().concat(" "));
            featureFile.getElements().forEach(
                    element -> {
                        element.getSteps().forEach(
                                step -> {
                                    if (!step.getResult().getStatus().contains("passed")) failuresCount += 1;
                                    duration += step.getResult().getDuration();

                                    var keyword = step.getKeyword();
                                    var stepName = step.getName();
                                    var stepResult = step.getResult().getStatus();

                                    stringOutBuilder(element.getKeyword(), keyword, stepName, stepResult);
                                }
                        );
                    }
            );

            featureResult.setFailures(String.valueOf(failuresCount));
            featureResult.setTests(String.valueOf(parseJSON.size()));
            featureResult.setTime(String.valueOf(duration));

            testCaseResults.add(
                    new FeatureTestCaseResult()
                            .setTestName(featureFile.getName())
                            .setDescription(featureFile.getDescription())
                            .setCaseOutInfo(stepResults)
                    );
            stepResults = "";
        });

        featureResult.setTags(tempTags);
        featureResult.setTestCases(testCaseResults);

        return featureResult;
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

}
