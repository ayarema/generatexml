package com.easytestit.generatexml.configuration;

/**
 * Additional configuration class which is defining an additional scope of functionality
 * Currently we have additional functionality namely: zipping files to folder & sending zipped files on http result view server
 */
public enum ConfigureXMLMode {

    /**
     * Defines additional functionality that enables zipping results files to archive.
     */
    ZIP_XML_RESULT_TO_FILE,

    /**
     * Defines additional functionality that enables sending results archive file to report portal server.
     */
    SEND_RESULT_TO_RP
}
