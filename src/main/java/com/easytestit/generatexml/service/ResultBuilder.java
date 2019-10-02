package com.easytestit.generatexml.service;

import com.easytestit.generatexml.dto.feature.Feature;
import com.easytestit.generatexml.dto.result.FeatureResult;

import java.util.ArrayList;
import java.util.Collection;

public class ResultBuilder {

    private Collection<FeatureResult> aggregatedResults = new ArrayList<>();
    private Boolean isFailure = true;
    private Long duration = 0L;

    public Collection<FeatureResult> generateAggregatedReport(Collection<Feature> parseJSON) {
        parseJSON.forEach(feature -> {
            FeatureResult featureResult = new FeatureResult();

            feature.getElements().forEach(
                    featureElement -> {
                        featureElement.getSteps().forEach(
                                step -> {
                                    isFailure = (!step.getResult().getStatus().contains("passed"));
                                    duration += step.getResult().getDuration();
                                }
                        );
                    }
            );

            if (!isFailure) featureResult.setFailures("0");
            featureResult.setTests(String.valueOf(parseJSON.size()));
            featureResult.setTime(String.valueOf(duration));

            aggregatedResults.add(featureResult);
        });



        return aggregatedResults;
    }

}
