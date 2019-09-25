package com.iaremenko.generatexml;

import com.iaremenko.generatexml.configuration.Configuration;
import com.iaremenko.generatexml.configuration.ConfigurationMode;
import com.iaremenko.generatexml.data.DefaultData;
import com.iaremenko.generatexml.dto.ReportDocument;
import com.iaremenko.generatexml.service.DocProcessingXML;
import com.iaremenko.generatexml.service.SenderService;
import com.iaremenko.generatexml.service.DocumentXMLFilling;
import com.iaremenko.generatexml.utils.FileToZip;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GenerateXMLReport {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLReport.class.getName());
    private Configuration configuration;

    private DocProcessingXML processing = new DocProcessingXML();
    private DocumentXMLFilling service = new DocumentXMLFilling();
    private FileToZip toZip = new FileToZip();
    private SenderService senderService = new SenderService();

    public GenerateXMLReport() {
        LOGGER.log(Level.INFO, "Start application to convert JSON report to XML report");
    }

    public GenerateXMLReport(@NotNull Configuration configuration) {
        LOGGER.log(Level.INFO, "Start application to convert JSON report to XML report with specified Configuration");
        this.configuration = configuration;
    }

    /**
     * Functionality provided files and generates the XML report. When generating process fails
     * report with information about error is provided.
     */
    public GenerateXMLReport generateXMLreport() {
        //create new folder where new XML report will be created
        createResultsReportFolder();

        try {
            if (configuration != null) {

                configuration.addJsonFiles(getJsonFilesFrom(configuration.getReportFolder()));
                ParserJSONToXML parserJSONToXML = new ParserJSONToXML(configuration);

                //convert JSON file in XML
                parserJSONToXML.parseJSON()
                        .forEach(feature -> {
                            service.convertObjectToXML(feature);
                        });

                //create ZIP file from XML which created from previews step
                if (configuration.containsConfigurationMode(ConfigurationMode.ZIP_XML_RESULT_FILE)) toZip.createZip();
                return this;
            } else {
                //convert JSON file in XML
                service.convertObjectToXML(readTargetData());
                return null;
            }
        } catch (Exception e) {
            generateErrorReport(e);
            return null;
        }
    }

    public void sendXML() {
        try {
            senderService.sendFile(DefaultData.reportFolderZipName);
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    @NotNull
    private Collection<String> getJsonFilesFrom(@NotNull File reportFolder) {
        Collection<String> extensions = Collections.singletonList(DefaultData.defaultFileExtensions);
        Collection<String> out = new ArrayList<>();
        File[] files = reportFolder.listFiles();

        //might be null
        if (files == null) {
            LOGGER.log(Level.INFO, "Report folder ".concat(reportFolder.getName()).concat(" aren't contain any files!"));
            return out;
        }

        for (File file : files) {
            if (file.isFile() && extensions.contains(file.getName().substring(file.getName().lastIndexOf('.') + 1))) {
                out.add(String.valueOf(reportFolder)
                        .concat("\\")
                        .concat(file.getName()));
            }
        }

        return out;
    }

    private void generateErrorReport(Exception e) {
        LOGGER.log(Level.ERROR, "Unexpected error", e);
    }

    private void createResultsReportFolder() {
        LOGGER.log(Level.INFO, "Try to create new folder in project directory by default parameters");

        File resultsFolder = new File(DefaultData.reportResultsFolder);
        if (!resultsFolder.exists()) {
            if (resultsFolder.mkdirs()) {
                LOGGER.log(Level.INFO, "Direction ".concat(DefaultData.reportResultsFolder).concat(" was created"));
            }
        }
    }

    @Nullable
    private ReportDocument readTargetData() {
        LOGGER.log(Level.INFO, "Method readTargetData invoked");

        try (Reader reader = new FileReader(DefaultData.reportDirPath)) {
            String jsonString = IOUtils.toString(reader);
            String newJsonString = jsonString.substring(1, jsonString.length()-1);
            return processing.deserializeReportDocumentToObject(newJsonString);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
