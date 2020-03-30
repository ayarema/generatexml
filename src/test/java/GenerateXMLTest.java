import com.easytestit.generatexml.GenerateXML;
import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.easytestit.generatexml.data.ConfigDataProvider;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class GenerateXMLTest {

    @Test
    public void testGenerateXMLWithoutConfiguration() {
        String expectedProjectName = ConfigDataProvider.DEFAULT_PROJECT_NAME + ".xml";
        String result = prepareDocumentsForDefaultTesting();

        new GenerateXML().make();

        Assert.assertEquals(result + " and file was created: - ",
                expectedProjectName,
                getFileName("target/surefire-reports/", "xml"));
    }

    @Test
    public void testGenerateXMLWithConfiguration() {
        ConfigureXMLReport conf = new ConfigureXMLReport(
                "src/test/resources/",
                "Test Project",
                false,
                false
        );
        new GenerateXML(conf).make();

        Assert.assertEquals("TestProject.xml",
                getFileName("out/xml-report/", "xml"));
    }

    @Test
    public void testThatXMLFileWasCreated() {
        ConfigureXMLReport conf = new ConfigureXMLReport(
                "src/test/resources/",
                "Test Project",
                true,
                false
        );
        new GenerateXML(conf).make();

        Assert.assertEquals("TestProject.zip",
                getFileName("out/xml-report/", "zip"));
    }

    private String getFileName(String path, String type) {
        File[] files = new File(path).listFiles();

        if (files == null) {
            throw new GenerateXMLReportException("The directory where the report file is to be created is empty!");
        }

        return Arrays.stream(files).findFirst()
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(type)).get().getName();
    }

    private String prepareDocumentsForDefaultTesting() {
        String defaultReportsFolder = ConfigDataProvider.DEFAULT_FOLDER;

        File resultsFolder = new File(defaultReportsFolder);

        String result = !resultsFolder.exists() && resultsFolder.mkdirs()
                ? "Direction " + defaultReportsFolder + " created successfully"
                : "Direction " + defaultReportsFolder + " was already created";

        File[] origins = {
                new File("src/test/resources/test.customer.json"),
                new File("src/test/resources/test.users.json")
        };
        File[] destination = {
                new File("target/surefire-reports/test.customer.json"),
                new File("target/surefire-reports/test.users.json")
        };
        try {
            for (int i = 0; i < origins.length; i++) {
                Files.copy(origins[i].toPath(), destination[i].toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return result;
    }
}
