package com.easytestit.generatexml.http;

import com.easytestit.generatexml.data.ConfigDataProvider;

/**
 * Use this {@link HttpURLBuilder} class for getting simple String of needed URL
 * which you want to use when connect to report server
 */
public class HttpURLBuilder {

    public static HttpURLBuilder create() {
        return new HttpURLBuilder();
    }

    protected HttpURLBuilder() {
        super();
    }

    public String getHostProjectUrl() {
        //http://{host}/api/v1/project/{project name}
        return ConfigDataProvider.URL
                .concat(ConfigDataProvider.VERSION)
                .concat(ConfigDataProvider.PROJECT)
                .concat(ConfigDataProvider.PROJECT_NAME);
    }

    public String getHostCreateProjectUrl() {
        //http://{host}/api/v1/project
        return ConfigDataProvider.URL
                .concat(ConfigDataProvider.VERSION)
                .concat(ConfigDataProvider.PROJECT);
    }

    public String getHostImportFileUrl() {
        //http://{host}/api/v1/{project name}/launch/import
        return ConfigDataProvider.URL
                .concat(ConfigDataProvider.VERSION)
                .concat(ConfigDataProvider.PROJECT_NAME)
                .concat(ConfigDataProvider.SERVICE_URL);
    }
}
