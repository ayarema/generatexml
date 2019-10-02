package com.easytestit.generatexml;

import com.easytestit.generatexml.configuration.Configuration;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.easytestit.generatexml.dto.feature.Feature;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class ParserJSONFiles {

    private static final Logger LOGGER = LogManager.getLogger(XMLReportApplication.class.getName());
    private Configuration configuration;
    private final ObjectMapper mapper = new ObjectMapper();

    ParserJSONFiles() {}

    ParserJSONFiles(Configuration configuration) {
        LOGGER.log(Level.INFO, "Start parse JSON report to Object with specified Configuration");
        this.configuration = configuration;

        //added specific parameters to mapper
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        //enable injection functionality
        InjectableValues values = new InjectableValues.Std().addValue(Configuration.class, configuration);
        mapper.setInjectableValues(values);
    }

    Collection<Feature> parseJSON() {
        return getReportDocumentsList(configuration.getJsonFiles());
    }

    Collection<Feature> parseJSON(Collection<String> jsonFiles) {
        return getReportDocumentsList(jsonFiles);
    }

    private Collection<Feature> getReportDocumentsList(Collection<String> jsonFiles) {
        Collection<Feature> features = new ArrayList<>();

        if (jsonFiles.isEmpty()) {
            throw new ValidationException("None JSON report files was added!");
        }

        for (String jsonFile : jsonFiles) {
            Feature[] reportDocuments_ = parseForReportDocuments(jsonFile);
            features.addAll(Arrays.asList(reportDocuments_));
        }

        if (features.isEmpty()) {
            throw new ValidationException("Passed files have no specified Cucumber standard report! Please check your JSON reports in target direction");
        }
        return features;
    }

    /**
     * Describe functionality where JSON files are parsed in java Object
     * @param jsonFile JSON files with a Cucumber standard describing the result of running tests
     * @return ReportDocument object with values from JSON files
     */
    private Feature[] parseForReportDocuments(String jsonFile) {
        try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            Feature[] features = mapper.readValue(reader, Feature[].class);
            if (ArrayUtils.isEmpty(features)) {
                LOGGER.log(Level.INFO, "File '{0}' does not contain karate reports", jsonFile);
            }
            return features;
        } catch (IOException e) {
            throw new ValidationException(e);
        }
    }
}
