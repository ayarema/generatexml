package com.easytestit.generatexml.http;

import com.easytestit.generatexml.data.DefaultData;
import okhttp3.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SenderService {

    private static final Logger LOGGER = LogManager.getLogger(SenderService.class.getName());

    private OkHttpClient client = new OkHttpClient();

    public void post(String file) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", DefaultData.reportFolderZipName,
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(file)))
                .build();

        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer a330ba8c-46c9-4ca2-ba3e-f35cdbe42dfb")
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Accept", "application/json")
                .url("http://localhost:8080".concat("/api/v1/generatexml/launch/import"))
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code ".concat(String.valueOf(response)));

            LOGGER.log(Level.INFO, Objects.requireNonNull(response.body()).string());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
