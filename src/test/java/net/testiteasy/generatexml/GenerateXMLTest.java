package net.testiteasy.generatexml;

import net.testiteasy.generatexml.configuration.ConfigureXMLReport;
import net.testiteasy.generatexml.data.ConfigDataProvider;

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
        prepareDocumentsForDefaultTesting();

        new GenerateXML().make();

        Assert.assertEquals(
                expectedProjectName,
                getFileName("out/xml-report/", "xml"));
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
    public void testThatZIPFileWasCreated() {
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
            throw new GenerateXMLReportException("The directory " + "'"+ path + "'" + " where the report file is to be created is empty!");
        }

        return Arrays.stream(files)
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith(type))
                .findFirst().get().getName();
    }

    private void prepareDocumentsForDefaultTesting() {
        String defaultReportsFolder = ConfigDataProvider.DEFAULT_FOLDER;
        File resultsFolder = new File(defaultReportsFolder);

        if (!resultsFolder.exists()) {
            resultsFolder.mkdirs();
        }

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
    }
}
