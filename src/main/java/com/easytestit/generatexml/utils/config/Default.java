package com.easytestit.generatexml.utils.config;

import com.easytestit.generatexml.GenerateXMLReportException;

public enum Default {
    PATH_TO_REPORTS("default.folder.path"),
    PROJECT_NAME("default.project.name");

    private final String key;

    Default(String key) {
        if (key != null) {
            this.key = key;
        } else {
            throw new GenerateXMLReportException("Argument key should not be null but is null");
        }
    }

    /**
     * @return String value from configuration file associated with this key
     */
    public String getString() {
        return ConfigLoader.getValue(this.key);
    }
}
