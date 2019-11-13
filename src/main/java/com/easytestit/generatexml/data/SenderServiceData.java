package com.easytestit.generatexml.data;

public enum SenderServiceData {

    AUTHORIZATION("Authorization"),
    BEARER("Bearer"),
    CONTENT_TYPE("Content-Type"),
    ACCEPT("Accept"),
    FORM_DATA("multipart/form-data"),
    APPLICATION_JSON("application/json"),
    OCTET_STREAM("application/octet-stream");

    private String value;

    SenderServiceData(String str) {
        value = str;
    }

    public String getValue() {
        return this.value;
    }
}
