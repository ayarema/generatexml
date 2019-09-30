package com.easytestit.generatexml.configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Configuration {

    private static final Logger LOGGER = LogManager.getLogger(Configuration.class.getName());
    private File reportFolder;
    private Collection<String> jsonFiles;
    private Collection<ConfigurationMode> configurationMode = new ArrayList<>();

    public Configuration(File reportFolder) {
        LOGGER.log(Level.INFO, "Create configuration for application with defined report folder");
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
     * @return the folder where new XML report should be created
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
     * @param configurationMode parameter that include specific functionality
     */
    public void addConfigurationMode(ConfigurationMode configurationMode) {
        this.configurationMode.add(configurationMode);
    }

    /**
     * Describe method which check that needed parameter exist or added before for specific functionality
     * @param configurationMode parameter which should be checked
     * @return boolean value if passed parameter exist in configuration mode
     */
    public boolean containsConfigurationMode(ConfigurationMode configurationMode) {
        return this.configurationMode.contains(configurationMode);
    }
}
