package com.easytestit.generatexml.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The main configuration class which is defining additional functionality and how our application will work
 */
public class ConfigureXMLReport {

    private static final Logger LOGGER = LogManager.getLogger(ConfigureXMLReport.class.getName());
    private File reportFolder;
    private Collection<String> jsonFiles;
    private Collection<ConfigureXMLMode> configureXMLMode = new ArrayList<>();

    public ConfigureXMLReport(@NotNull File reportFolder) {
        LOGGER.info(String.format("Create configuration for application with defined '%s' report folder", reportFolder.getAbsolutePath()));
        this.reportFolder = reportFolder;
    }

    /**
     * Method which added JSON files to new list
     * @param jsonFiles the list which stored all JSON files which after should be converted to XML
     */
    public void addJsonFiles(Collection<String> jsonFiles) {
        this.jsonFiles = jsonFiles;
    }

    /**
     * @return the folder where JSON reports locate which should be convert to XML
     */
    public File getReportFolder() {
        return reportFolder;
    }

    /**
     * @return the list of files which should be converted from JSON to XML
     */
    public Collection<String> getJsonFiles() {
        return jsonFiles;
    }

    /**
     * Describe functionality where user wants to include zipping functionality
     * @param configureXMLMode parameter that include specific functionality
     */
    public void addConfigureXMLMode(ConfigureXMLMode configureXMLMode) {
        this.configureXMLMode.add(configureXMLMode);
    }

    /**
     * Describe method which check that needed parameter exist or added before for specific functionality
     * @param configureXMLMode parameter which should be checked
     * @return boolean value if passed parameter exist in configuration mode
     */
    public boolean containsConfigurationMode(ConfigureXMLMode configureXMLMode) {
        return this.configureXMLMode.contains(configureXMLMode);
    }
}
