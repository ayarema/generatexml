package com.iaremenko.generatexml.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

public class Configuration {

    private static final Logger LOGGER = LogManager.getLogger(Configuration.class.getName());
    private File reportFolder;
    private List<String> filesToConvert;

    public Configuration(File reportFolder) {
        LOGGER.log(Level.INFO, "Create configuration for application with defined report folder");
        this.reportFolder = reportFolder;
    }

    /**
     * Method which added JSON files to new list
     * @param filesToConvert the list which stored all JSON files which after should be converted to XML
     */
    public void addFilesToConvert(List<String> filesToConvert) {
        this.filesToConvert = filesToConvert;
    }

    /**
     * @return the folder where new XML report should be created
     */
    public File getReportFolder() {
        return reportFolder;
    }

    /**
     * @return the list of files which should be converted from JSON to XML
     */
    public List<String> getFilesToConvert() {
        return filesToConvert;
    }
}
