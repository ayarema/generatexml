import com.easytestit.generatexml.GenerateXML;
import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class GenerateXMLTest {

    @Description("The test which check that functionality successfully create .xml file without configuration, simple default parameters")
    @Test
    public void testGenerateXMLWithoutConfiguration() {
        new GenerateXML().make();
        Assert.assertEquals(
                "AggregatedReport.xml",
                getFileName("target/surefire-reports/", "xml"));
    }

    @Description("The test which check that functionality successfully create .xml file with passed configuration")
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

    @Description("The test which check that functionality successfully create .zip file with passed configuration for zipping")
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
}
