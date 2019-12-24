package com.easytestit.generatexml.configuration;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * The main configuration class which is defining additional functionality and how our application will work
 */
public class ConfigureXMLReport {

    private static final Logger LOGGER = LogManager.getLogger(ConfigureXMLReport.class.getName());
    @Getter
    private final String path;
    @Getter
    private final File source;
    @Getter
    private final boolean reportAsZip;
    @Getter
    private final boolean sendReport;

    /**
     * Configuration by default (no zip, no report)
     *
     * @param path path to source folder
     */
    public ConfigureXMLReport(@NotNull final String path) {
        LOGGER.info(String.format("Create configuration with defined path '%s' to source folder", path));
        this.path = path;
        this.source = new File(path);
        this.reportAsZip = false;
        this.sendReport = false;
    }

    /**
     * Full configuration
     *
     * @param path path to source folder
     * @param reportAsZip report as zip
     * @param sendReport send report to report portal
     */
    public ConfigureXMLReport(
            @NotNull final String path,
            final boolean reportAsZip,
            final boolean sendReport
    ) {
        LOGGER.info(String.format("Create configuration with defined path '%s' to source folder", path));
        this.path = path;
        this.source = new File(path);
        this.reportAsZip = reportAsZip;
        this.sendReport = sendReport;
    }
}
