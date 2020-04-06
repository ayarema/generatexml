package net.testiteasy.generatexml;

import net.testiteasy.generatexml.configuration.ConfigureXMLReport;
import net.testiteasy.generatexml.data.ConfigDataProvider;
import net.testiteasy.generatexml.http.SenderService;
import net.testiteasy.generatexml.service.ReportService;
import net.testiteasy.generatexml.service.Serialization;
import net.testiteasy.generatexml.service.ZipService;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GenerateXML {

    private static final Logger LOGGER = Logger.getLogger(GenerateXML.class.getName());
    private final ConfigureXMLReport configuration;

    /**
     * You can use default constructor of a {@link GenerateXML()} class
     * only if you need to use functionality with default parameters.
     * <p>
     * Default parameter located in gxml.properties file.
     */
    public GenerateXML() {
        this.configuration = new ConfigureXMLReport(
                ConfigDataProvider.DEFAULT_FOLDER,
                ConfigDataProvider.DEFAULT_PROJECT_NAME,
                false,
                false);
    }

    /**
     * Constructor a {@link GenerateXML()} class with {@link ConfigureXMLReport()} as a parameter
     * is necessary when you need to use functionality with your specific configuration data.
     *
     * @param configuration the parameter which store specific configuration
     */
    public GenerateXML(final ConfigureXMLReport configuration) {
        if (configuration == null)
            throw new GenerateXMLReportException("Argument configuration should not be null but is null");
        else this.configuration = configuration;
    }

    /**
     * This method is the entry point to the application for converting files
     * from JSON to XML. This method analyzes the configuration, which may have been passed
     * to the constructor of class {@link GenerateXML()}.
     * <p>
     * It is also analyzed depending on the configuration, what will be done.
     * <p>For example, if the configuration was transferred without creating a
     * ZIP of the archive, then this functionality {@link ZipService ()#createZip()}
     * for creating the archive will not be launched.
     * <p/>
     * <p>
     * When generating process fails report with information about error is provided.
     */
    public void make() {
        //create new folder where new XML report will be created
        createResultsReportFolder();

        try {
            //convert JSON file in XML
            convert(resolvePath(new File(configuration.getPath())), configuration.getProjectName());
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
        } catch (Exception e) {
            throw new GenerateXMLReportException("Error during XML report generation");
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
            throw new GenerateXMLReportException("Arguments pathList, projectName should not be null but are null");
        }
    }

    /**
     * The method which provides the ability to create a directory that will later serve as the folder
     * where the aggregated XML report will be created
     * <p>
     * Also, this folder will zip later if needed configuration will pass
     */
    private void createResultsReportFolder() {
        File resultsFolder = new File(this.configuration.getPath());
        LOGGER.log(Level.INFO, !resultsFolder.exists() && resultsFolder.mkdirs()
                ? "Direction " + resultsFolder.getName() + " created successfully"
                : "Direction " + resultsFolder.getName() + " was already created"
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
        Collection<String> filePathList;
        if (source == null) {
            throw new GenerateXMLReportException("Parameter 'source' where located JSON files should not be null but is null");
        } else {
            File[] files = source.listFiles();

            if (files == null || files.length == 0) {
                throw new GenerateXMLReportException("Provided source folder " + source.getName() + " doesn't contain any files!");
            }

            filePathList = Arrays.stream(files)
                    .filter(File::isFile)
                    .filter(file -> file.getName().endsWith("json"))
                    .map(file -> source.getPath()
                            .concat(String.valueOf(File.separatorChar))
                            .concat(file.getName())
                    )
                    .collect(Collectors.toList());

            if (filePathList.isEmpty()) {
                throw new GenerateXMLReportException(String.format("Provided source folder %s doesn't contain files with JSON format!", source.getName()));
            }
        }
        return filePathList;
    }
}
