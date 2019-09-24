package com.iaremenko.generatexml;

import com.iaremenko.generatexml.configuration.Configuration;
import com.iaremenko.generatexml.data.TestData;
import com.iaremenko.generatexml.dto.ReportDocumentDto;
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

public class GenerateXMLApplication {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLApplication.class.getName());
    private Configuration configuration;
    private ParserJSONToXML parserJSONToXML;

    private DocProcessingXML processing = new DocProcessingXML();
    private DocumentXMLFilling service = new DocumentXMLFilling();
    private FileToZip toZip = new FileToZip();
    private SenderService senderService = new SenderService();

    public GenerateXMLApplication() {
        LOGGER.info("Start application to convert JSON to XML");
    }

    public GenerateXMLApplication(Configuration configuration) {
        LOGGER.info("Start application to convert JSON to XML with specified Configuration");
        this.configuration = configuration;
        this.parserJSONToXML = new ParserJSONToXML(configuration);
    }

    /**
     * Functionality provided files and generates the XML report. When generating process fails
     * report with information about error is provided.
     */
    public GenerateXMLApplication generateXML() {
        try {
            if (configuration != null) {
                //create new folder where new XML report will create
                createReportFolder(configuration.getReportFolder());

                //convert JSON file in XML
                service.convertObjectToXML(readTargetData());

                //create ZIP file from XML which created from previews step
                toZip.createZip();
                return this;
            } else {
                //if configuration null system will create report folder by default parameters
                createReportFolder();

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
            senderService.sendFile(TestData.reportFolderZipName);
        } catch (Exception e) {
            generateErrorReport(e);
        }
    }

    private void generateErrorReport(Exception e) {
        LOGGER.log(Level.INFO, "Unexpected error", e);
    }

    private void createReportFolder() {
        LOGGER.log(Level.INFO, "Try to create new folder in project directory by default parameters");

        File newFolder = new File(TestData.reportFolder);
        if (!newFolder.exists()) {
            if (newFolder.mkdirs()) {
                LOGGER.log(Level.INFO, "Direction ".concat(TestData.reportFolder).concat(" was created"));
            }
        }
    }

    private void createReportFolder(@NotNull File reportFolder) {
        LOGGER.log(Level.INFO, "Try to create new folder in project directory");

        if (!reportFolder.exists()) {
            if (reportFolder.mkdirs()) {
                LOGGER.log(Level.INFO, "Direction ".concat(String.valueOf(reportFolder)).concat(" was created"));
            }
        }
    }

    @Nullable
    private ReportDocumentDto readTargetData() {
        LOGGER.info("Method readTargetData invoked");

        try (Reader reader = new FileReader(TestData.filePath)) {
            String jsonString = IOUtils.toString(reader);
            String newJsonString = jsonString.substring(1, jsonString.length()-1);
            return processing.createObjectFromDoc(newJsonString);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
