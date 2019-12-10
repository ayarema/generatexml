package com.easytestit.generatexml;

import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.easytestit.generatexml.configuration.ConfigureXMLMode;
import com.easytestit.generatexml.http.SenderService;
import com.easytestit.generatexml.service.TransformService;
import com.easytestit.generatexml.data.DefaultData;
import com.easytestit.generatexml.service.GenerateXMLResult;
import com.easytestit.generatexml.service.XMLServiceExtended;
import com.easytestit.generatexml.service.ZipService;
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
    private XMLServiceExtended xmlService = new GenerateXMLResult();

    public GenerateXMLReport() {
        LOGGER.info("Starting application to convert JSON report to XML report with default parameters");
    }

    public GenerateXMLReport(@NotNull ConfigureXMLReport configureXMLReport) {
        LOGGER.info("Starting application to convert JSON report to XML report with specified Configuration");
        this.configureXMLReport = configureXMLReport;
    }

    /**
     * Functionality provided files and generates the aggregated XML report.
     * When generating process fails
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
                        new TransformService().transformFeaturesToReportSuites(
                                new ParserJSONFiles(configureXMLReport).parseJSON()));

                //create ZIP file from XML which created from previews step
                if (configureXMLReport.containsConfigurationMode(ConfigureXMLMode.ZIP_XML_RESULT_TO_FILE)) createZip();
                if (configureXMLReport.containsConfigurationMode(ConfigureXMLMode.SEND_RESULT_TO_RP)) sendXML();
            } else {
                //when configuration is null start functionality with default parameters
                xmlService.convertObjectToXML(
                        new TransformService().transformFeaturesToReportSuites(
                                new ParserJSONFiles().parseJSON(
                                        getJsonFilesFrom(
                                                new File(DefaultData.TARGET_FOLDER_PATH)))));
            }
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    /**
     * Provides the ability to create a directory that will later serve as the folder
     * where the aggregated XML report will be created
     *
     * Also, this folder will zip later if needed configuration will pass
     */
    private void createResultsReportFolder() {
        LOGGER.info("Try to create new folder in project directory");
        File resultsFolder = new File(DefaultData.REPORT_RESULTS_FOLDER);
        LOGGER.info(!resultsFolder.exists() && resultsFolder.mkdirs() ? "Direction ".concat(DefaultData.REPORT_RESULTS_FOLDER).concat(" was created") : "Direction ".concat(DefaultData.REPORT_RESULTS_FOLDER).concat(" was already created"));
    }

    /**
     * A convenience method which read files with specific extension
     * and collects path to these files in a new collection
     *
     * @param reportFolder folder where locate all files after launching tests
     * @return collection of String with defined path to JSON files
     */
    @NotNull
    private Collection<String> getJsonFilesFrom(@NotNull File reportFolder) {
        var extensions = Collections.singletonList(DefaultData.DEFAULT_FILE_EXTENSIONS);
        Collection<String> out = new ArrayList<>();
        File[] files = reportFolder.listFiles();

        //might be null
        if (files == null) {
            LOGGER.info("Report folder ".concat(reportFolder.getName()).concat(" aren't contain any files!"));
            return out;
        }

        //analyze files in folder and filter by specified extension and collect these to new collection
        Arrays.stream(files).forEach(file -> {
            if (file.isFile() && extensions.contains(file.getName().substring(file.getName().lastIndexOf('.') + 1))) {
                out.add(String.valueOf(reportFolder)
                        .concat(String.valueOf(File.separatorChar))
                        .concat(file.getName()));
            } else {
                LOGGER.info("Report folder ".concat(reportFolder.getName())
                        .concat(" doesn't contain files with JSON format!"));
            }
        });

        return out;
    }

    /**
     * Provides the ability to create a zip file with report folder where store aggregated XML report
     * This functionality will work if needed configuration will pass
     */
    private void createZip() {
        try {
            new ZipService().createZip();
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    /**
     * Provides the ability to send a zipped report folder to specified server
     * if needed configuration will pass
     */
    private void sendXML() {
        try {
            new SenderService().post(DefaultData.FILE_ZIP_NAME);
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    /**
     * A convenience method to use the error messages in many places
     *
     * @param e exception object
     * The detail message which will print in a console later.
     */
    private void generateErrorReport(Exception e) {
        LOGGER.log(Level.ERROR, "Unexpected error", e);
    }

}
