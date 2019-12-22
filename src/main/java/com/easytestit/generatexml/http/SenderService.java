package com.easytestit.generatexml.http;

import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.data.ConfigDataProvider;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.Request;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * HTTP client class created for making POST request and sending files to server
 */
public class SenderService {

    private static final Logger LOGGER = LogManager.getLogger(SenderService.class.getName());
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Use this method if you need send POST request to the server
     *
     * @param file which should be added in POST request
     */
    public void post(@NonNull final String file) {
        final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file, RequestBody.Companion.create(new File(file), MEDIA_TYPE))
                .build();

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + ConfigDataProvider.TOKEN)
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Accept", "application/json")
                .url(ConfigDataProvider.URL
                        .concat(ConfigDataProvider.VERSION)
                        .concat(ConfigDataProvider.PROJECT_NAME)
                        .concat(ConfigDataProvider.SERVICE_URL))
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new GenerateXMLReportException(
                        String.format("Received unexpected response code %s and message %s from server",
                                response.code(),
                                response.message()
                        )
                );
            }

            LOGGER.log(Level.INFO, response.body() == null ? "Empty response body" : response.body());
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Error during send request to the server");
            e.printStackTrace();
        }
    }
}
