package com.iaremenko.generatexml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaremenko.generatexml.configuration.Configuration;
import com.iaremenko.generatexml.dto.ReportDocument;
import com.iaremenko.generatexml.service.DocProcessingXML;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class ParserJSONToXML {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLReport.class.getName());
    private Configuration configuration;
    private final ObjectMapper mapper = new ObjectMapper();

    ParserJSONToXML(Configuration configuration) {
        LOGGER.log(Level.INFO, "Start parse JSON report to Object with specified Configuration");
        this.configuration = configuration;

        //added specific parameters to mapper
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        //enable injection functionality
        InjectableValues values = new InjectableValues.Std().addValue(Configuration.class, configuration);
        mapper.setInjectableValues(values);
    }

    Collection<ReportDocument> parseJSON() {
        Collection<ReportDocument> reportDocuments = new ArrayList<>();

        if (configuration.getJsonFiles().isEmpty()) {
            throw new ValidationException("None JSON report files was added!");
        }

        for (String jsonFile : configuration.getJsonFiles()) {
                ReportDocument[] reportDocuments_ = parseForReportDocuments(jsonFile);
                reportDocuments.addAll(Arrays.asList(reportDocuments_));
        }

        if (reportDocuments.isEmpty()) {
            throw new ValidationException("Passed files have no specified Cucumber standard report! Please check your JSON reports in target direction");
        }

        return reportDocuments;
    }

    /**
     * Describe functionality where JSON files are parsed in java Object
     * @param jsonFile JSON files with a Cucumber standard describing the result of running tests
     * @return ReportDocument object with values from JSON files
     */
    private ReportDocument[] parseForReportDocuments(String jsonFile) {
        try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8)) {
            ReportDocument[] reportDocuments = mapper.readValue(reader, ReportDocument[].class);
            if (ArrayUtils.isEmpty(reportDocuments)) {
                LOGGER.log(Level.INFO, "File '{0}' does not contain karate reports", jsonFile);
            }
            return reportDocuments;
        } catch (IOException e) {
            throw new ValidationException(e);
        }
    }
}
