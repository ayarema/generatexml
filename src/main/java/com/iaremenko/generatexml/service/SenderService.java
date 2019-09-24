package com.iaremenko.generatexml.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

/**
 * @link SenderService class was created for connection to specific sefver
 * and sent to this server needed files
 */
public class SenderService {

    private static final Logger LOGGER = LogManager.getLogger(SenderService.class.getName());

    private Properties appProps = new Properties();
    private Process process;

    public SenderService() {
        LOGGER.log(Level.INFO, "Start to call sender functionality");
    }

    public void sendFile(String fileName) {
        try {
            LOGGER.info("Method sendFile invoked");
            ProcessBuilder processBuilder = new ProcessBuilder(Objects.requireNonNull(commandBuilder(fileName)).split(" "));
            process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            int exitCode = process.exitValue();

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            process.destroy();
        }
    }

    @Nullable
    private String commandBuilder(String zipFileName) {
        try {
            LOGGER.info("Method commandBuilder invoked");
            String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            String curlConfigPath = rootPath + "curl.properties";

            File propertyFile = new File(curlConfigPath);

            if (propertyFile.exists())
                appProps.load(new FileInputStream(curlConfigPath));
                return appProps.getProperty("request.curl")
                        .concat(" ")
                        .concat(appProps.getProperty("request.first.argument").concat(" "))
                        .concat(appProps.getProperty("request.method").concat(" "))
                        .concat(appProps.getProperty("request.url").concat(" "))
                        .concat(appProps.getProperty("request.header").concat(" "))
                        .concat(appProps.getProperty("request.application").concat(" "))
                        .concat(appProps.getProperty("request.header").concat(" "))
                        .concat(appProps.getProperty("request.authorization").concat(" "))
                        .concat(appProps.getProperty("request.header").concat(" "))
                        .concat(appProps.getProperty("request.content.type").concat(" "))
                        .concat(appProps.getProperty("request.second.argument"))
                        .concat(zipFileName);
        } catch (NullPointerException | FileNotFoundException e) {
            LOGGER.error("There's missing file curl.properties for sending new file via curl functionality");
            LOGGER.error(e.getMessage());
            return null;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
