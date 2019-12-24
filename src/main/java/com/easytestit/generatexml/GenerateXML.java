package com.easytestit.generatexml;

import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.easytestit.generatexml.data.ConfigDataProvider;
import com.easytestit.generatexml.http.SenderService;
import com.easytestit.generatexml.service.ReportService;
import com.easytestit.generatexml.service.Serialization;
import com.easytestit.generatexml.service.ZipService;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class GenerateXML {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXML.class.getName());
    private ConfigureXMLReport configuration;

    public GenerateXML() {
        LOGGER.info("Starting application to convert JSON report to XML report with default parameters");
    }

    public GenerateXML(@NotNull final ConfigureXMLReport configuration) {
        LOGGER.info("Starting application to convert JSON report to XML report with specified Configuration");
        this.configuration = configuration;
    }

    /**
     * <p> Method describes functionality which provided files and generates the aggregated XML report.
     * When generating process fails report with information about error is provided.
     * </p>
     */
    public void make() {
        //create new folder where new XML report will be created
        createResultsReportFolder();

        try {
            if (configuration != null) {
                LOGGER.info("Try to convert JSON files according to provided configuration");

                //convert JSON file in XML
                convert(resolvePath(configuration.getSource()));

                //create ZIP file from XML which created from previews step
                boolean isZipCreated = false;
                if (configuration.isReportAsZip()) {
                    isZipCreated = new ZipService().createZip();
                }

                // send XML file to report server
                if (configuration.isSendReport() && isZipCreated) {
                    new SenderService().post(ConfigDataProvider.ZIP_NAME);
                } else {
                    LOGGER.info(isZipCreated
                            ? "Zip was created, but no need to send it"
                            : "Zip was not created. Nothing to send to report server"
                    );
                }

            } else {
                //when configuration is null start functionality with default parameters
                LOGGER.info("No configuration passed, try to convert JSON files from target directory");

                convert(resolvePath(new File(ConfigDataProvider.TARGET_FOLDER_PATH)));
            }

        } catch (Exception e) {
            LOGGER.error("Error during XML report generation");
            throw new GenerateXMLReportException(e);
        }
    }

    private void convert(@NonNull final Collection<String> pathList) {
        new Serialization().serializeToXML(
                new ReportService().transformFeaturesToReport(
                        new ParseJSON().parse(pathList))
        );
    }

    /**
     * Void function which provides the ability to create a directory that will later serve as the folder
     * where the aggregated XML report will be created
     * </p>
     * Also, this folder will zip later if needed configuration will pass
     */
    private void createResultsReportFolder() {
        LOGGER.info("Try to create new folder in project directory");

        String path = ConfigDataProvider.REPORT_RESULTS_FOLDER;
        File resultsFolder = new File(path);

        LOGGER.info(!resultsFolder.exists() && resultsFolder.mkdirs()
                ? "Direction " + path + " created successfully"
                : "Direction " + path + " was already created"
        );
    }

    /**
     * A convenience method which read files with specific extension
     * and collects path to these files in a new String collection
     * </p>
     *
     * @param source the folder where locate all files after launching tests
     * @return the collection of String with defined path to JSON files
     */
    @NotNull
    private Collection<String> resolvePath(@NotNull final File source) {
        File[] files = source.listFiles();

        //might be null
        if (files == null) {
            LOGGER.info("Provided source folder " + source.getName() + " doesn't contain any files!");
            return Collections.emptyList();
        }

        Collection<String> filePathList = Arrays.stream(files)
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith("json"))
                .map(file -> String.valueOf(source)
                        .concat(String.valueOf(File.separatorChar))
                        .concat(file.getName())
                )
                .collect(Collectors.toList());

        if (filePathList.isEmpty()) {
            LOGGER.info("Provided source folder " + source.getName() + " doesn't contain files with JSON format!");
        }

        return filePathList;
    }
}
