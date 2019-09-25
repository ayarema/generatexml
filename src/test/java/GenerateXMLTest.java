import com.iaremenko.generatexml.GenerateXMLReport;
import com.iaremenko.generatexml.configuration.Configuration;
import com.iaremenko.generatexml.configuration.ConfigurationMode;
import org.junit.Test;

import java.io.File;

public class GenerateXMLTest {

    @Test
    public void generateXmlTest() {
        GenerateXMLReport application = new GenerateXMLReport();
        application.generateXMLreport().sendXML();
    }

    @Test
    public void generateXmlTestWithConf() {
        Configuration conf = new Configuration(new File("target/surefire-reports/"));
        conf.addConfigurationMode(ConfigurationMode.ZIP_XML_RESULT_FILE);
        conf.addConfigurationMode(ConfigurationMode.SEND_RESULT_TO_REPORT_PORTAL);
        GenerateXMLReport appWithConf = new GenerateXMLReport(conf);
        appWithConf.generateXMLreport().sendXML();
    }
}
