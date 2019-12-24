package com.easytestit.generatexml;

import com.easytestit.generatexml.dto.input.Feature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class {@link ParseJSON} describes functionality where JSON files will deserialize to Java Objects
 */
@SuppressWarnings("unchecked")
class ParseJSON {

    private static final Logger LOGGER = LogManager.getLogger(ParseJSON.class.getName());

    /**
     * Make a list of Features objects which was deserialized from JSON files
     *
     * @param pathList path to real files which locate in compiled folder
     * @return Collection of Features objects with deserialized values
     */
    @NotNull
    public Collection<Feature> parse(@NotNull final Collection<String> pathList) {
        Collection<Feature> features = new ArrayList<>();

        if (pathList.isEmpty()) {
            throw new GenerateXMLReportException("Empty argument provided");
        }

        pathList.forEach(path -> {
            Collection<Feature> reportFeatures = parseFeatures(path);
            if (reportFeatures != null && !reportFeatures.isEmpty()) {
                LOGGER.info(String.format("File '%s' contains %d features", path, reportFeatures.size()));
                features.addAll(reportFeatures);
            }
        });

        if (features.isEmpty()) {
            throw new GenerateXMLReportException("Feature files not found");
        }

        return features;
    }

    /**
     * Describe functionality where JSON files are parsed in java Object
     *
     * @param path JSON files with a Cucumber standard describing the result of running tests
     * @return ReportDocument object with values from JSON files
     */
    private Collection<Feature> parseFeatures(@NonNull final String path) {
        Type collectionType = new TypeToken<Collection<Feature>>() {
        }.getType();

        try {
            return (Collection<Feature>) new Gson().fromJson(
                    new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8),
                    collectionType
            );
        } catch (FileNotFoundException e) {
            throw new GenerateXMLReportException(e);
        }
    }
}
