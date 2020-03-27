package com.easytestit.generatexml.utils.config;

import com.easytestit.generatexml.GenerateXMLReportException;

public enum Sender {
    TOKEN("rp.token"),
    URL("rp.url"),
    VERSION("rp.api.version"),
    PROJECT("rp.project.project"),
    PROJECT_NAME("rp.project.name"),
    SERVICE_URL("rp.service.url");

    private final String key;

    Sender(final String key) {
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
