package com.easytestit.generatexml.data;

import com.easytestit.generatexml.utils.config.Default;
import com.easytestit.generatexml.utils.config.Sender;

/**
 * Predefined data needed for the functionality to work correctly Generate-XML library
 */
public class ConfigDataProvider {

    public static final String DEFAULT_FOLDER = Default.PATH_TO_REPORTS.getString();
    public static final String DEFAULT_PROJECT_NAME = Default.PROJECT_NAME.getString();

    public static final String ZIP_EXTENSION = ".zip";
    public static final String ZIP_NAME = "zipAggregatedReport.zip";
    public static final String REPORT_RESULTS_FOLDER = "out/xml-report/";

    public static final String TOKEN = Sender.TOKEN.getString();

    public static final String URL = Sender.URL.getString();
    public static final String VERSION = Sender.VERSION.getString();
    public static final String SERVICE_URL = Sender.SERVICE_URL.getString();
    public static final String PROJECT = Sender.PROJECT.getString();
    public static final String PROJECT_NAME = Sender.PROJECT_NAME.getString();
}
