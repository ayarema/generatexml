package com.easytestit.generatexml.http;

import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.data.ConfigDataProvider;
import com.easytestit.generatexml.http.fluent.HttpHeaders;
import com.easytestit.generatexml.http.fluent.HttpMethods;
import com.easytestit.generatexml.http.io.HttpCloseMode;
import com.easytestit.generatexml.http.io.HttpCloseable;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic implementation of class {@link CloseableHttpClient}.
 * In this implementation are presented the basic principles of working with HTTP methods,
 * server requests and their processing.
 */
public class HttpSimpleClient extends CloseableHttpClient {

    private static final Logger LOGGER = Logger.getLogger(CloseableHttpClient.class.getName());

    private String jsonString;
    private final ConcurrentLinkedQueue<Closeable> closeables;

    public HttpSimpleClient(final List<Closeable> closeables) {
        super();
        this.closeables = closeables != null ?  new ConcurrentLinkedQueue<>(closeables) : null;
    }

    @Override
    protected HttpSimpleResponse doExecute() {
        return null;
    }

    @Override
    public HttpSimpleClient postJson(String jsonString) {
        this.jsonString = jsonString;
        return this;
    }

    @Override
    public HttpSimpleResponse executePost(String hostUrl, String fileLocation) {
        HttpURLConnection con = null;
        File fileToUpload = null;

        int statusCode;
        String reasonPhrase;
        String content;

        try {
            final URL url = new URL(hostUrl);
            fileToUpload = new File(fileLocation);
            String boundaryString = UUID.randomUUID().toString();;

            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(HttpHeaders.CONNECTION_TIMEOUT);
            con.setReadTimeout(HttpHeaders.CONNECTION_TIMEOUT);

            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestProperty(HttpHeaders.AUTHORIZATION, ConfigDataProvider.TOKEN);
            con.addRequestProperty(HttpHeaders.CONTENT_TYPE, "multipart/form-data");

            //con.connect();

            System.out.println("Start to posting file " + fileLocation + " ....... ");
            FileInputStream is = new FileInputStream(fileToUpload);
            OutputStream os = con.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytes_read;    // How many bytes in buffer
            while((bytes_read = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes_read);
            }
            os.close();
            is.close();

            System.out.println("Done...");

            statusCode = con.getResponseCode();
            reasonPhrase = con.getResponseMessage();
            content = readInputStream(con);

        } catch (IOException e) {
            throw new GenerateXMLReportException("Something went wrong! See detailed stack trace: ", e);
        } finally {
            if (con != null)
                con.disconnect();
        }
        return new HttpSimpleResponse(statusCode, reasonPhrase, content);
    }

    public String getJsonString() {
        return this.jsonString;
    }

    @Override
    public void close(HttpCloseMode var1) {
        if (this.closeables != null) {
            Closeable closeable;
            while ((closeable = this.closeables.poll()) != null) {
                try {
                    if (closeable instanceof HttpCloseable) {
                        ((HttpCloseable) closeable).close(var1);
                    } else {
                        closeable.close();
                    }
                } catch (final IOException ex) {
                    LOGGER.log(Level.INFO, ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    public void close() {
        close(HttpCloseMode.GRACEFUL);
    }

    @Override
    public HttpSimpleResponse execute(HttpMethods methods, String host_url) {
        HttpURLConnection con = null;

        int statusCode;
        String reasonPhrase;
        String content;

        try {
            final URL url = new URL(host_url);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(HttpHeaders.CONNECTION_TIMEOUT);
            con.setReadTimeout(HttpHeaders.CONNECTION_TIMEOUT);
            con.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");

            switch (methods) {
                case GET:
                    con.setRequestMethod("GET");

                    con.addRequestProperty(HttpHeaders.ACCEPT, "application/json");
                    con.addRequestProperty(HttpHeaders.AUTHORIZATION, ConfigDataProvider.TOKEN);

                    break;
                case PUT:
                    con.setRequestMethod("PUT");
                    break;
                case POST:
                    //curl -X POST

                    // --header 'Content-Type: multipart/form-data'
                    // --header 'Accept: application/json'
                    // --header 'Authorization: bearer e7bec0f1-4cd4-4acd-a84f-7d3d24e424f2'

                    // {"type":"formData"}
                    // 'http://localhost:8080/api/v1/superadmin_personal/launch/import'
                    con.setRequestMethod("POST");

                    con.setDoOutput(true);
                    con.addRequestProperty(HttpHeaders.ACCEPT, "application/json");
                    con.addRequestProperty(HttpHeaders.AUTHORIZATION, ConfigDataProvider.TOKEN);

                    try(OutputStream os = con.getOutputStream()) {
                        byte[] input = getJsonString().getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

                    break;
                case DELETE:
                    con.setRequestMethod("DELETE");

                    con.addRequestProperty(HttpHeaders.ACCEPT, "application/json");
                    con.addRequestProperty(HttpHeaders.AUTHORIZATION, ConfigDataProvider.TOKEN);

                    break;
            }

            statusCode = con.getResponseCode();
            reasonPhrase = con.getResponseMessage();
            content = readInputStream(con);

        } catch (IOException e) {
            throw new GenerateXMLReportException("Looks like you passed incorrect URL. " +
                    "Please check it and look in stack trace: " + e.getMessage());
        } finally {
            if (con != null)
                con.disconnect();
        }
        return new HttpSimpleResponse(statusCode, reasonPhrase, content);
    }

    private String readInputStream(final HttpURLConnection con){
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine.trim());
            }
            return content.toString();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
