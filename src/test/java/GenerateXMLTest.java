import com.easytestit.generatexml.GenerateXML;
import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class GenerateXMLTest {

    @Test
    public void testThatXMLFileWasCreated() {
        final var path = "out/xml-report/";
        final var projectName = "testProject";

        var conf = new ConfigureXMLReport(
                //"src/test/resources/",
                null,
                projectName,
                false,
                false
        );
        new GenerateXML(conf).make();

        File[] files = new File(path).listFiles();

        if (files == null) {
            throw new GenerateXMLReportException("The directory where the report file is to be created is empty!");
        }

        String fileName = Arrays.stream(files).findFirst()
                .filter(File::isFile)
                .filter(file -> file.getName().endsWith("xml")).get().getName();
        Assert.assertEquals(projectName + ".xml", fileName);
    }
}
