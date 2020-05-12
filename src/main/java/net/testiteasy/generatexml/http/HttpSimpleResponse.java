package net.testiteasy.generatexml.http;

import net.testiteasy.generatexml.http.utils.HttpCoreUtils;

/**
 * The base implementation of class {@link HttpResponse}.
 * This {@link HttpSimpleResponse} class describes the actions for processing HTTP responses from the server.
 */
public class HttpSimpleResponse implements HttpResponse {

    private int code;
    private String reasonPhrase;
    private String responseBody;

    public HttpSimpleResponse(final int code, final String reasonPhrase, final String responseBody) {
        super();
        this.code = code;
        this.reasonPhrase = reasonPhrase;
        this.responseBody = responseBody;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public void setCode(int code) {
        HttpCoreUtils.positive(code, "Status code");
        this.code = code;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override
    public void setReasonPhrase(String reason) {
        this.reasonPhrase = reason;
    }

    @Override
    public String getResponseBody() {
        return this.responseBody;
    }

    @Override
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
