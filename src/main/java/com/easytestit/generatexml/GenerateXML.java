package com.easytestit.generatexml;

import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.easytestit.generatexml.data.ConfigDataProvider;
import com.easytestit.generatexml.http.SenderService;
import com.easytestit.generatexml.service.ReportService;
import com.easytestit.generatexml.service.Serialization;
import com.easytestit.generatexml.service.ZipService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GenerateXML {

    private static final Logger LOGGER = Logger.getLogger(GenerateXML.class.getName());
    private ConfigureXMLReport configuration;

    public GenerateXML() {
    }

    public GenerateXML(final ConfigureXMLReport configuration) {
        if (configuration != null)
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

                //convert JSON file in XML
                convert(resolvePath(configuration.getSource()), configuration.getProjectName());

                //create ZIP file from XML which created from previews step
                boolean isZipCreated = false;
                if (configuration.isReportAsZip()) {
                    isZipCreated = configuration.getProjectName() != null
                            ? new ZipService().createZip(configuration.getProjectName())
                            : new ZipService().createZip(ConfigDataProvider.ZIP_NAME);
                }

                // send XML file to report server
                if (configuration.isSendReport() && isZipCreated) {
                    new SenderService().post(ConfigDataProvider.ZIP_NAME);
                } else {
                    LOGGER.log(Level.INFO, isZipCreated
                            ? "Zip was created, but no need to send it"
                            : "Zip was not created. Nothing to send to report server"
                    );
                }

            } else {
                //when configuration is null start functionality with default parameters
                convert(resolvePath(new File("target/surefire-reports/")), "AggregatedReport");
            }

        } catch (Exception e) {
            throw new GenerateXMLReportException("Error during XML report generation ", e);
        }
    }

    private void convert(final Collection<String> pathList, final String projectName) {
        if (pathList != null & projectName != null) {
            new Serialization().serializeToXML(
                    new ReportService().transformFeaturesToReport(
                            new ParseJSON().parse(pathList)),
                    projectName
            );
        } else {
            throw new GenerateXMLReportException("Arguments pathList, projectName should not be null but are null. See detailed stack trace: ", new NullPointerException());
        }
    }

    /**
     * Void function which provides the ability to create a directory that will later serve as the folder
     * where the aggregated XML report will be created
     * </p>
     * Also, this folder will zip later if needed configuration will pass
     */
    private void createResultsReportFolder() {
        String path = ConfigDataProvider.REPORT_RESULTS_FOLDER;
        File resultsFolder = new File(path);

        LOGGER.log(Level.INFO, !resultsFolder.exists() && resultsFolder.mkdirs()
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
    private Collection<String> resolvePath(final File source) {
        Collection<String> filePathList = new ArrayList<>();
        if (source != null) {
            File[] files = source.listFiles();

            if (files == null) {
                throw new GenerateXMLReportException("Provided source folder " + source.getName() + " doesn't contain any files!");
            }

            filePathList = Arrays.stream(files)
                    .filter(File::isFile)
                    .filter(file -> file.getName().endsWith("json"))
                    .map(file -> String.valueOf(source)
                            .concat(String.valueOf(File.separatorChar))
                            .concat(file.getName())
                    )
                    .collect(Collectors.toList());

            if (filePathList.isEmpty()) {
                throw new GenerateXMLReportException("Provided source folder " + source.getName() + " doesn't contain files with JSON format!");
            }
        }
        return filePathList;
    }
}
