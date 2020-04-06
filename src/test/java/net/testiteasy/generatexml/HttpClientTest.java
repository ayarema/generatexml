package net.testiteasy.generatexml;

import net.testiteasy.generatexml.http.CloseableHttpClient;
import net.testiteasy.generatexml.http.HttpClient;
import net.testiteasy.generatexml.http.HttpClientBuilder;
import net.testiteasy.generatexml.http.HttpURLBuilder;
import net.testiteasy.generatexml.http.fluent.HttpMethods;
import net.testiteasy.generatexml.http.HttpSimpleClients;
import net.testiteasy.generatexml.http.HttpSimpleResponse;
import net.testiteasy.generatexml.http.fluent.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HttpClientTest {

    private final HttpClient client = HttpClientBuilder.create().build();
    private HttpSimpleResponse response;

    @Test
    public void statusOKGETRequestTest() {
        response = client.execute(HttpMethods.GET, HttpURLBuilder.create().getHostProjectUrl());
        assertEquals(response.getCode(), HttpStatus.STATUS_CODE_OK);
        assertEquals(response.getReasonPhrase(), "OK");
    }

    @Test
    public void responseIsNotEmptyGETRequestTest() {
        response = client.execute(HttpMethods.GET, HttpURLBuilder.create().getHostProjectUrl());
        assertThat("They are equal!", String.valueOf(response.getResponseBody().contains("projectId")), is(String.valueOf(true)));
    }

    @Test
    public void statusOKPOSTRequestTest() {
        String jsonStr = "{\"addInfo\":\"Project about Auto\",\"customer\":\"TOV AutoVita\",\"entryType\":\"INTERNAL\",\"projectName\":\"AUTOMOTIVE\"}";

        CloseableHttpClient client1 = HttpSimpleClients.createDefault();
        response = client1.postJson(jsonStr).execute(HttpMethods.POST, HttpURLBuilder.create().getHostCreateProjectUrl());

        int statusCode = response.getCode();
        String responseBody = response.getResponseBody();

        assertEquals(statusCode, HttpStatus.STATUS_CODE_CREATED);
        assertThat("They are equal!", String.valueOf(responseBody.contains("id")), is(String.valueOf(true)));
    }

    @Test
    public void statusOKDELETERequestTest() {
        response = client.execute(HttpMethods.DELETE, "http://localhost:8080/api/v1/project/automotive");
        assertEquals(response.getCode(), HttpStatus.STATUS_CODE_OK);
    }

    @Test
    public void statusOKPOSTFileRequestTest() {
        String fileLocation = "out/xml-report/zipTestProject.zip";

        CloseableHttpClient client1 = HttpSimpleClients.createDefault();
        response = client1.executePost(HttpURLBuilder.create().getHostImportFileUrl(), fileLocation);

        assertEquals(response.getCode(), HttpStatus.STATUS_CODE_OK);
        assertThat("They are equal!", String.valueOf(response.getResponseBody().contains("msg")), is(String.valueOf(true)));
    }
}
