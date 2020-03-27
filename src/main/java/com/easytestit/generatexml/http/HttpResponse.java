package com.easytestit.generatexml.http;

import com.easytestit.generatexml.http.fluent.HttpStatus;

/**
 * This interface {@link HttpResponse} presents the basic methods
 * that can serve in the future for processing responses from servers after HTTP requests
 */
public interface HttpResponse {

    /**
     * Obtains the code of this response message.
     *
     * @return  the status code.
     */
    int getCode();

    /**
     * Updates status code of this response message.
     *
     * @param code the HTTP status code.
     *
     * @see HttpStatus
     */
    void setCode(int code);

    /**
     * Obtains the reason phrase of this response if available.
     *
     * @return  the reason phrase.
     */
    String getReasonPhrase();

    /**
     * Updates the status line of this response with a new reason phrase.
     *
     * @param reason    the new reason phrase as a single-line string, or
     *                  {@code null} to unset the reason phrase
     */
    void setReasonPhrase(String reason);

    /**
     * Obtains the response body of request if available.
     *
     * @return  the response body.
     */
    String getResponseBody();

    /**
     * Updates response body of this message.
     *
     * @param responseBody the HTTP response body message.
     *
     * @see HttpStatus
     */
    void setResponseBody(String responseBody);

}
