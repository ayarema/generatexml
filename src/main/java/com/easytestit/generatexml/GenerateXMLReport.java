package com.easytestit.generatexml;

import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.easytestit.generatexml.configuration.ConfigureXMLMode;
import com.easytestit.generatexml.http.SenderService;
import com.easytestit.generatexml.service.AggregatedXMLReportBuilder;
import com.easytestit.generatexml.data.DefaultData;
import com.easytestit.generatexml.service.GenerateXMLResult;
import com.easytestit.generatexml.service.XMLServiceExtended;
import com.easytestit.generatexml.utils.FileToZip;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class GenerateXMLReport {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLReport.class.getName());
    private ConfigureXMLReport configureXMLReport;
    private GenerateXMLResult generateXMLResult = new GenerateXMLResult();
    private XMLServiceExtended xmlService = new GenerateXMLResult();
    private FileToZip toZip = new FileToZip();

    public GenerateXMLReport() {
        LOGGER.info("Start application to convert JSON report to XML report with default parameters");
    }

    public GenerateXMLReport(@NotNull ConfigureXMLReport configureXMLReport) {
        LOGGER.info("Start application to convert JSON report to XML report with specified Configuration");
        this.configureXMLReport = configureXMLReport;
    }

    /**
     * Functionality provided files and generates the XML report. When generating process fails
     * report with information about error is provided.
     */
    public void generateXMLreport() {
        //create new folder where new XML report will be created
        createResultsReportFolder();

        try {
            if (configureXMLReport != null) {
                //read JSON files from compiled directory
                configureXMLReport.addJsonFiles(getJsonFilesFrom(configureXMLReport.getReportFolder()));
                //convert JSON file in XML
                xmlService.convertObjectToXML(
                        new AggregatedXMLReportBuilder().generateAggregatedXMLReport(
                                new ParserJSONFiles(configureXMLReport).parseJSON()));

                //create ZIP file from XML which created from previews step
                if (configureXMLReport.containsConfigurationMode(ConfigureXMLMode.ZIP_XML_RESULT_TO_FILE)) createZip();
                if (configureXMLReport.containsConfigurationMode(ConfigureXMLMode.SEND_RESULT_TO_RP)) sendXML();
            } else {
                //when configuration is null start functionality with default parameters
                new ParserJSONFiles().parseJSON(
                        getJsonFilesFrom(
                                new File(DefaultData.reportDirPath)))
                        .forEach(feature -> generateXMLResult.convertObjectToXML(feature));
            }
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    private void createZip() {
        try {
            toZip.createZip();
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    private void sendXML() {
        try {
            new SenderService().post(DefaultData.reportFolderZipName);
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    /**
     * Functionality which read files with specific extension and collect path to this files in new collection
     * @param reportFolder folder where locate all files after launching tests
     * @return collection of String with defined path to JSON files
     */
    @NotNull
    private Collection<String> getJsonFilesFrom(@NotNull File reportFolder) {
        var extensions = Collections.singletonList(DefaultData.defaultFileExtensions);
        Collection<String> out = new ArrayList<>();
        File[] files = reportFolder.listFiles();

        //might be null
        if (files == null) {
            LOGGER.log(Level.INFO, "Report folder ".concat(reportFolder.getName()).concat(" aren't contain any files!"));
            return out;
        }

        Arrays.stream(files).forEach(file -> {
            if (file.isFile() && extensions.contains(file.getName().substring(file.getName().lastIndexOf('.') + 1))) {
                out.add(String.valueOf(reportFolder)
                        .concat(String.valueOf(File.separatorChar))
                        .concat(file.getName()));
            } else {
                LOGGER.info("Report folder ".concat(reportFolder.getName())
                        .concat(" aren't contain files with JSON format!"));
            }
        });

        return out;
    }

    private void generateErrorReport(Exception e) {
        LOGGER.log(Level.ERROR, "Unexpected error", e);
    }

    /**
     * Describe creating folder for XML report result
     * This folder will zip later if needed configuration will pass
     */
    private void createResultsReportFolder() {
        LOGGER.info("Try to create new folder in project directory");
        File resultsFolder = new File(DefaultData.reportResultsFolder);
        if (!resultsFolder.exists() && resultsFolder.mkdirs())
            LOGGER.info("Direction ".concat(DefaultData.reportResultsFolder).concat(" was created"));
    }
}
