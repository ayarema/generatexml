package com.iaremenko.generatexml;

import com.iaremenko.generatexml.data.TestData;
import com.iaremenko.generatexml.dto.ReportDocumentDto;
import com.iaremenko.generatexml.service.DocProcessingXML;
import com.iaremenko.generatexml.service.SenderService;
import com.iaremenko.generatexml.service.DocumentXMLFilling;
import com.iaremenko.generatexml.utils.FileToZip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class GenerateXMLApplication {
    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLApplication.class);
    private DocProcessingXML processing = new DocProcessingXML();
    private DocumentXMLFilling service = new DocumentXMLFilling();
    private FileToZip toZip = new FileToZip();
    private SenderService senderService = new SenderService();

    public void generateXML() {
        LOGGER.info("Start application Convert JSON to XML and send it to ReportPortal");
        makeFolder();
        service.convertObjectToXML(readTargetData());
        toZip.createZip();
        senderService.sendFile(TestData.reportFolderZipName);
    }

    private void makeFolder() {
        LOGGER.info("Try to create new folder in project directory");
        File newFolder = new File(TestData.reportFolder);
        if (!newFolder.exists()) {
            if (newFolder.mkdirs()) {
                LOGGER.info("Direction ".concat(TestData.reportFolder).concat(" was created"));
            }
        }
    }

    @Nullable
    private ReportDocumentDto readTargetData() {
        try (Reader reader = new FileReader(TestData.filePath)) {
            LOGGER.info("Method readTargetData invoked");
            String jsonString = IOUtils.toString(reader);
            String newJsonString = jsonString.substring(1, jsonString.length()-1);
            return processing.createObjectFromDoc(newJsonString);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
