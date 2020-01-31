package com.easytestit.generatexml;

import com.easytestit.generatexml.dto.input.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class {@link ParseJSON} describes functionality where JSON files will deserialize to Java Objects
 */
@SuppressWarnings("unchecked")
public class ParseJSON {

    private final ObjectMapper mapper = new ObjectMapper();

    public ParseJSON() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // this prevents printing eg. 2.20 as 2.2
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
    }

    /**
     * Make a list of Features objects which was deserialized from JSON files
     *
     * @param pathList path to real files which locate in compiled folder
     * @return Collection of Features objects with deserialized values
     */
    public Collection<Feature> parse(final Collection<String> pathList) {
        if (pathList != null) {
            Collection<Feature> features = new ArrayList<>();

            if (pathList.isEmpty()) {
                throw new GenerateXMLReportException("Empty argument provided");
            }

            pathList.forEach(path -> {
                Collection<Feature> reportFeatures = parseFeatures(path);
                if (reportFeatures != null && !reportFeatures.isEmpty()) {
                    features.addAll(reportFeatures);
                }
            });

            if (features.isEmpty()) {
                throw new GenerateXMLReportException("Feature files not found");
            }

            return features;
        } else {
            throw new GenerateXMLReportException("Argument pathList should not be null but is null. See detailed stack trace: ");
        }
    }

    /**
     * Describe functionality where JSON files are parsed in java Object
     *
     * @param path JSON files with a Cucumber standard describing the result of running tests
     * @return ReportDocument object with values from JSON files
     */
    private Collection<Feature> parseFeatures(final String path) {
        try (Reader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {
            return (Collection<Feature>) mapper.readValue(reader, new TypeReference<Collection<Feature>>(){});
        } catch (IOException e) {
            throw new GenerateXMLReportException(e);
        }
    }
}
