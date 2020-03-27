package com.easytestit.generatexml;

/**
 * Wrapper over standard error handling functionality.
 * Use this {@link GenerateXMLReportException} class to display the error message
 * that was processed into the console.
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
