package com.easytestit.generatexml;

/**
 * Wrapper over standard error handling functionality.
 * Use this {@link GenerateXMLReportException} class to display the error message
 * that was processed into the console.
 */
public class GenerateXMLReportException extends RuntimeException {

    public GenerateXMLReportException(final String message) {
        super(message);
    }
}
