package net.testiteasy.generatexml.http;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * The main idea of this class is to provide the possibility of further implementation
 * of the HTTP client, especially {@link CloseableHttpClient}, with a specific settings.
 *
 * At the moment, this is just creating an instance of {@link CloseableHttpClient} class.
 */
public final class HttpClientBuilder {

    private List<Closeable> closeables = null;

    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    protected HttpClientBuilder() {
        super();
    }

    public CloseableHttpClient build() {
        List<Closeable> closeablesCopy = closeables != null ? new ArrayList<>(closeables) : null;
        return new HttpSimpleClient(closeablesCopy);
    }

}
