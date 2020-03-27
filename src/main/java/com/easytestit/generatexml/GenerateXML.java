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

    /**
     * Constructor a {@link GenerateXML()} class without parameters is necessary
     * only if you need to use functionality with default parameters.
     *
     * Default parameter located in code in hardcode view.
     */
    public GenerateXML() {
    }

    /**
     * Constructor a {@link GenerateXML()} class with {@link ConfigureXMLReport()} as a parameter
     * is necessary when you need to use functionality with your specific configuration data.
     *
     * @param configuration the parameter which store specific configuration
     */
    public GenerateXML(final ConfigureXMLReport configuration) {
        if (configuration != null)
            this.configuration = configuration;
    }

    /**
     * This method is the entry point to the application for converting files
     * from JSON to XML. This method analyzes the configuration, which may have been passed
     * to the constructor of class {@link GenerateXML()}.
     *
     * It is also analyzed depending on the configuration, what will be done.
     * <p>For example, if the configuration was transferred without creating a
     * ZIP of the archive, then this functionality {@link com.easytestit.generatexml.service.ZipService()#createZip()}
     * for creating the archive will not be launched.
     * <p/>
     *
     * When generating process fails report with information about error is provided.
     */
    public void make() {
        //create new folder where new XML report will be created
        createResultsReportFolder();

        try {
            if (configuration != null) {
                if (configuration.getSource() == null) {
                    configuration.setSource(ConfigDataProvider.DEFAULT_FOLDER);
                }
                if (configuration.getProjectName() == null) {
                    configuration.setProjectName(ConfigDataProvider.PROJECT_NAME);
                }
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
                convert(resolvePath(new File(ConfigDataProvider.DEFAULT_FOLDER)), ConfigDataProvider.DEFAULT_PROJECT_NAME);
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
     * The method which provides the ability to create a directory that will later serve as the folder
     * where the aggregated XML report will be created
     *
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
