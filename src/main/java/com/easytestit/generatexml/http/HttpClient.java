package com.easytestit.generatexml.http;

import com.easytestit.generatexml.http.fluent.HttpMethods;

/**
 * This interface {@link HttpClient} is presented as a basic contract for making HTTP requests.
 * It does not contain any restrictions or specific details on the query execution process
 * and leaves the specifics of state management, authentication to individual implementations.
 */
public interface HttpClient {

    <T> T execute();

    HttpSimpleResponse execute(HttpMethods method, String host_url);

    <T> T postJson(String jsonString);
}
