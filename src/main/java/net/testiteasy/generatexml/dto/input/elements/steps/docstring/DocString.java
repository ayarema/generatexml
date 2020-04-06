package net.testiteasy.generatexml.dto.input.elements.steps.docstring;

/**
 * This file describes the contract model of the message data,
 * which stores an explanation of the result of the successful or unsuccessful step.
 */
public class DocString {

    public DocString() {

    }

    private String content_type;

    private String value;

    private Integer line;

    public String getContent_type() {
        return content_type;
    }

    public String getValue() {
        return value;
    }

    public Integer getLine() {
        return line;
    }
}
