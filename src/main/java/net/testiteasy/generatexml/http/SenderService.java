package net.testiteasy.generatexml.http;

import net.testiteasy.generatexml.GenerateXMLReportException;
import net.testiteasy.generatexml.data.ConfigDataProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.Request;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HTTP client class created for making POST request and sending files to server
 * with dependency on OkHttpClient
 */
public class SenderService {

    private static final Logger LOGGER = Logger.getLogger(SenderService.class.getName());
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Use this method if you need send POST request to the server
     *
     * @param file which should be added in POST request
     */
    public void post(final String file) {
        if (file != null) {
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

                LOGGER.log(Level.INFO, response.body() == null
                        ? "Empty response body"
                        : response.body().toString());
            } catch (IOException e) {
                throw new GenerateXMLReportException("Error during send request to the server: ");
            }
        } else {
            throw new GenerateXMLReportException("Argument file should not be null but is null. See detailed stack trace: ");
        }
    }
}
