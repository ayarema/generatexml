package com.easytestit.generatexml;

import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.easytestit.generatexml.dto.feature.Feature;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class ParserJSONFiles {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLReport.class.getName());
    private ConfigureXMLReport configureXMLReport;
    private final ObjectMapper mapper = new ObjectMapper();

    ParserJSONFiles() {

    }

    ParserJSONFiles(ConfigureXMLReport configureXMLReport) {
        LOGGER.info("Start parse JSON report to Object with specified Configuration");
        this.configureXMLReport = configureXMLReport;
        //added specific parameters to mapper
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        //enable injection functionality
        InjectableValues values = new InjectableValues.Std().addValue(ConfigureXMLReport.class, configureXMLReport);
        mapper.setInjectableValues(values);
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
            Feature[] reportFeatures = parseForFeatureDocuments(jsonFile);
            LOGGER.info(String.format("File '%s' contains %d features", jsonFile, reportFeatures.length));
            features.addAll(Arrays.asList(reportFeatures));
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
    private Feature[] parseForFeatureDocuments(String jsonFile) {
        try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            Feature[] features = mapper.readValue(reader, Feature[].class);
            if (ArrayUtils.isEmpty(features)) {
                LOGGER.log(Level.INFO, String.format("Files %s does not contain karate reports", jsonFile));
            }
            return features;
        } catch (IOException e) {
            throw new ValidationException(e);
        }
    }
}
