package com.easytestit.generatexml.configuration;

import com.easytestit.generatexml.GenerateXMLReportException;

import java.io.File;

/**
 * The main configuration class which is defining additional functionality and how our application will work
 */
public class ConfigureXMLReport {

    private final String projectName;
    private final File source;
    private final boolean reportAsZip;
    private final boolean sendReport;

    /**
     * Configuration by default (no zip, no report)
     *
     * @param path path to source folder
     */
    public ConfigureXMLReport(final String path, final String projectName) {
        if (path != null) {
            this.source = new File(path);
        } else {
            throw new GenerateXMLReportException("Argument path should not be null but is null. See detailed stack trace: ", new NullPointerException());
        }
        if (projectName != null) {
            this.projectName = projectName;
        } else {
            throw new GenerateXMLReportException("Argument projectName should not be null but is null. See detailed stack trace: ", new NullPointerException());
        }
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
            final String path,
            final String projectName,
            final boolean reportAsZip,
            final boolean sendReport
    ) {
        if (path != null) {
            this.source = new File(path);
        } else {
            throw new GenerateXMLReportException("Argument path should not be null but is null. See detailed stack trace: ", new NullPointerException());
        }
        if (projectName != null) {
            this.projectName = projectName;
        } else {
            throw new GenerateXMLReportException("Argument projectName should not be null but is null. See detailed stack trace: ", new NullPointerException());
        }
        this.reportAsZip = reportAsZip;
        this.sendReport = sendReport;
    }

    public String getProjectName() {
        return projectName;
    }

    public File getSource() {
        return source;
    }

    public boolean isReportAsZip() {
        return reportAsZip;
    }

    public boolean isSendReport() {
        return sendReport;
    }
}
