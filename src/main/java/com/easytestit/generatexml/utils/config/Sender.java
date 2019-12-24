package com.easytestit.generatexml.utils.config;

import lombok.NonNull;

public enum Sender {
    TOKEN("rp.token"),
    URL("rp.url"),
    VERSION("rp.api.version"),
    PROJECT_NAME("rp.project.name"),
    SERVICE_URL("rp.service.url");

    private final String key;

    Sender(@NonNull final String key) {
        this.key = key;
    }

    /**
     * @return String value from configuration file associated with this key
     */
    public String getString() {
        return ConfigLoader.getValue(this.key);
    }
}
