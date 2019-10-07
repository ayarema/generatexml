package com.easytestit.generatexml.service;

import com.easytestit.generatexml.dto.feature.Feature;
import com.easytestit.generatexml.dto.result.FeatureResult;
import com.easytestit.generatexml.dto.result.testcase.FeatureTestCaseResult;
import com.easytestit.generatexml.dto.result.testcase.systemout.FeatureCaseStepResult;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ResultBuilder {

    private Boolean isFailure = true;
    private Long duration = 0L;

    public FeatureResult generateAggregatedReport(@NotNull Collection<Feature> parseJSON) {
        FeatureResult featureResult = new FeatureResult();
        Collection<String> tempTags = new ArrayList<>(Collections.emptyList());

        FeatureTestCaseResult testCaseResult = new FeatureTestCaseResult();
        Collection<FeatureTestCaseResult> testCaseResults = new ArrayList<>(Collections.emptyList());

        FeatureCaseStepResult stepResult = new FeatureCaseStepResult();
        Collection<FeatureCaseStepResult> stepResults = new ArrayList<>(Collections.emptyList());

        parseJSON.forEach(featureFile -> {

            featureFile.getTags().forEach(tag -> tempTags.add(tag.getName()));
            testCaseResult.setTestName(featureFile.getName());
            testCaseResult.setDescription(featureFile.getDescription());

            featureFile.getElements().forEach(
                    featureElement -> {

                        var keywordType = featureElement.getKeyword();

                        featureElement.getSteps().forEach(

                                step -> {
                                    isFailure = (!step.getResult().getStatus().contains("passed"));
                                    duration += step.getResult().getDuration();

                                    var keyword = step.getKeyword();
                                    var stepName = step.getName();
                                    var stepResult_ = step.getResult().getStatus();

                                    stepResult.setSystemOut(stringOutBuilder(keywordType, keyword, stepName, stepResult_));

                                    stepResults.add(stepResult);
                                }
                        );
                    }
            );

            testCaseResult.setCaseOutInfo(stepResults);

            if (!isFailure) featureResult.setFailures("0");
            featureResult.setTests(String.valueOf(parseJSON.size()));
            featureResult.setTime(String.valueOf(duration));

            testCaseResults.add(testCaseResult);
        });

        featureResult.setTags(tempTags);
        featureResult.setTestCases(testCaseResults);

        return featureResult;
    }

    @NotNull
    @Contract(pure = true)
    private String stringOutBuilder(String keywordType, String keyword, String stepName, String stepResult_) {
        var outLength = 10;
        var outLengthSecond = 100;
        StringBuilder outString = new StringBuilder(keywordType);

        while (outString.length() < outLength) outString.append(".");
        outString.append(keyword.concat(" ").concat(stepName));
        while (outString.length() < outLengthSecond) outString.append(".");
        outString.append(stepResult_);

        return outString.toString();
    }

}
