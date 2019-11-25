package com.easytestit.generatexml.data;

import org.jetbrains.annotations.Contract;

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
