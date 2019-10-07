package com.easytestit.generatexml.http;

import com.easytestit.generatexml.data.SenderServiceData;
import okhttp3.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class SenderService {

    private static final Logger LOGGER = LogManager.getLogger(SenderService.class.getName());

    private OkHttpClient client = new OkHttpClient();
    private Properties appProps = new Properties();

    public void post(String file) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file,
                        RequestBody.create(MediaType.parse(SenderServiceData.OCTET_STREAM.getValue()),
                                new File(file)))
                .build();

        Request request = new Request.Builder()
                .addHeader(
                        SenderServiceData.AUTHORIZATION.getValue(), SenderServiceData.BEARER.getValue().concat(" ")
                                .concat(Objects.requireNonNull(getProperties("rp.token"))))
                .addHeader(SenderServiceData.CONTENT_TYPE.getValue(), SenderServiceData.FORM_DATA.getValue())
                .addHeader(SenderServiceData.ACCEPT.getValue(), SenderServiceData.APPLICATION_JSON.getValue())
                .url(Objects.requireNonNull(getProperties("rp.url")).concat(serviceUrlBuilder()))
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code ".concat(String.valueOf(response)));

            LOGGER.log(Level.INFO, Objects.requireNonNull(response.body()).string());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Nullable
    private String getProperties(String property) {
        LOGGER.log(Level.INFO, "Method getProperties invoked");
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String curlConfigPath = rootPath + "sender.properties";

        try {
            File propertyFile = new File(curlConfigPath);
            if (propertyFile.exists())
                appProps.load(new FileInputStream(curlConfigPath));
            return appProps.getProperty(property);
        } catch (NullPointerException | FileNotFoundException e) {
            LOGGER.error(String.format("There's missing file %s for sending new file via curl functionality", "sender.properties"));
            LOGGER.error(e.getMessage());
            return null;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    private String serviceUrlBuilder() {
        return Objects.requireNonNull(getProperties("rp.api.version"))
                .concat(Objects.requireNonNull(getProperties("rp.project.name")))
                .concat(Objects.requireNonNull(getProperties("rp.service.url")));
    }
}
