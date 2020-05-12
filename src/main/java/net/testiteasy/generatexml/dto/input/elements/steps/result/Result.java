package net.testiteasy.generatexml.dto.input.elements.steps.result;

/**
 * This object describes a contract data model in which the result of passing a step is stored.
 */
public class Result {

    public Result() {

    }

    private Long duration;

    private String error_message;

    private String status;

    public Long getDuration() {
        return duration;
    }

    public String getError_message() {
        return error_message;
    }

    public String getStatus() {
        return status;
    }
}
