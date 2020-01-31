package com.easytestit.generatexml;

/**
 *
 */
public class GenerateXMLReportException extends RuntimeException {

    public GenerateXMLReportException(final Exception e) {
        super(e);
    }

    public GenerateXMLReportException(final String message) {
        super(message);
    }

    public GenerateXMLReportException(final String message, final Exception exception) {
        super(message, exception);
    }
}
