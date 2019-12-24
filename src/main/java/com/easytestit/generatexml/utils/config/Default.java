package com.easytestit.generatexml.utils.config;

import lombok.NonNull;

public enum Default {
    FILE_NAME("file.name"),
    TARGET_FOLDER_PATH("target.folder.path"),
    REPORT_RESULTS_FOLDER("report.result.folder.path"),
    FILE_ZIP_NAME("file.zip.name");

    private final String key;

    Default(@NonNull final String key) {
        this.key = key;
    }

    /**
     * @return String value from configuration file associated with this key
     */
    public String getString() {
        return ConfigLoader.getValue(this.key);
    }
}
