package com.easytestit.generatexml.http;

import com.easytestit.generatexml.http.io.HttpCloseable;

/**
 * Base implementation of {@link HttpClient} that also implements {@link HttpCloseable}.
 */
public abstract class CloseableHttpClient implements HttpClient, HttpCloseable {

    @Override
    public HttpSimpleResponse execute() {
        return doExecute();
    }

    protected abstract HttpSimpleResponse doExecute();

    @Override
    public abstract HttpSimpleClient postJson(String jsonString);

    public abstract HttpSimpleResponse executePost(String hostUrl, String fileLocation);
}
