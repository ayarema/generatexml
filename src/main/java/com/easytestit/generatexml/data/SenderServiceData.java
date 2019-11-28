package com.easytestit.generatexml.data;

import org.jetbrains.annotations.Contract;

/**
 * Additional enum class which was created for storing special data for generate request to the server
 */
public enum SenderServiceData {

    AUTHORIZATION("Authorization"),
    BEARER("Bearer"),
    CONTENT_TYPE("Content-Type"),
    ACCEPT("Accept"),
    FORM_DATA("multipart/form-data"),
    APPLICATION_JSON("application/json"),
    OCTET_STREAM("application/octet-stream");

    private String value;

    @Contract(pure = true)
    SenderServiceData(String str) {
        value = str;
    }

    @Contract(pure = true)
    public String getValue() {
        return this.value;
    }
}
