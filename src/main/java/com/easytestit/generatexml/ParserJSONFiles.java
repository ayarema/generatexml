package com.easytestit.generatexml;

import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.easytestit.generatexml.dto.input.Feature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.NoArgsConstructor;
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

@SuppressWarnings("unchecked")
@NoArgsConstructor
class ParserJSONFiles {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLReport.class.getName());
    private ConfigureXMLReport configureXMLReport;

    ParserJSONFiles(ConfigureXMLReport configureXMLReport) {
        LOGGER.info("Start parse JSON report to Object with specified Configuration");
        this.configureXMLReport = configureXMLReport;
    }

    /**
     * Start parse JSON with predicate configuration
     * @return Collection of Features objects
     */
    Collection<Feature> parseJSON() {
        return getFeatureDocumentsList(configureXMLReport.getJsonFiles());
    }

    /**
     * Start parse JSON without predicate configuration
     * @param jsonFiles path to real files which locate in compiled folder
     * @return Collection of Features objects
     */
    Collection<Feature> parseJSON(Collection<String> jsonFiles) {
        return getFeatureDocumentsList(jsonFiles);
    }

    /**
     * Make a list of Features objects which was deserialized from JSON files
     * @param jsonFiles path to real files which locate in compiled folder
     * @return Collection of Features objects with deserialized values
     */
    @NotNull
    private Collection<Feature> getFeatureDocumentsList(@NotNull Collection<String> jsonFiles) {
        Collection<Feature> features = new ArrayList<>();

        if (jsonFiles.isEmpty()) {
            throw new ValidationException("None JSON report files was added!");
        }

        jsonFiles.forEach( jsonFile -> {
            Collection<Feature> reportFeatures = parseForFeatureDocuments(jsonFile);
            LOGGER.info(String.format("File '%s' contains %d features", jsonFile, reportFeatures.size()));
            features.addAll(reportFeatures);
        });

        if (features.isEmpty()) {
            throw new ValidationException(String.format("Passed files have no specified Cucumber standard report! Please check your JSON reports in %s direction", configureXMLReport.getReportFolder()));
        }

        return features;
    }

    /**
     * Describe functionality where JSON files are parsed in java Object
     * @param jsonFile JSON files with a Cucumber standard describing the result of running tests
     * @return ReportDocument object with values from JSON files
     */
    private Collection<Feature> parseForFeatureDocuments(String jsonFile) {
        Type collectionType = new TypeToken<Collection<Feature>>(){}.getType();
        try {
            return (Collection<Feature>) new Gson().fromJson(new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8), collectionType);
        } catch (FileNotFoundException e) {
            throw new ValidationException(e);
        }
    }
}
