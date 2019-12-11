package com.easytestit.generatexml.http;

import com.easytestit.generatexml.data.SenderServiceData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.Request;
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

/**
 * HTTP client class created for making POST request and sending files on server
 */
public class SenderService {

    private static final Logger LOGGER = LogManager.getLogger(SenderService.class.getName());

    private OkHttpClient client = new OkHttpClient();
    private Properties appProps = new Properties();

    /**
     * Use this method if you need send POST request to the server
     * @param file which should be added in POST request
     */
    public void post(String file) {
        final MediaType MEDIA_TYPE = MediaType.parse(SenderServiceData.OCTET_STREAM.getValue());
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file,
                        RequestBody.Companion.create(
                                new File(file), MEDIA_TYPE))
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

    /**
     * Helper method which is helping read values from keys from application properties
     *
     * @param property name which should be read
     * @return values which stored in key property
     */
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

    /**
     * Helper method which is building string from many others
     * @return concatenated string which includes values from keys from application property
     */
    @NotNull
    private String serviceUrlBuilder() {
        return Objects.requireNonNull(getProperties("rp.api.version"))
                .concat(Objects.requireNonNull(getProperties("rp.project.name")))
                .concat(Objects.requireNonNull(getProperties("rp.service.url")));
    }
}
