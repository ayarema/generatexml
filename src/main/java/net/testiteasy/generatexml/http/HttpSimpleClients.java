package net.testiteasy.generatexml.http;

/**
 * This {@link HttpSimpleClients} class describes the basic idea of creating
 * a new instance of class {@link CloseableHttpClient} with the default configuration.
 */
public final class HttpSimpleClients {

    private HttpSimpleClients() {
        super();
    }

    /**
     * Creates {@link CloseableHttpClient} instance with default
     * configuration.
     */
    public static CloseableHttpClient createDefault() {
        return HttpClientBuilder.create().build();
    }
}
