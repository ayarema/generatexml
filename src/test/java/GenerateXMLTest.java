import com.easytestit.generatexml.GenerateXML;
import com.easytestit.generatexml.configuration.ConfigureXMLMode;
import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import org.junit.Test;

import java.io.File;

public class GenerateXMLTest {

    @Test
    public void generateXmlTestWithConf() {
        //var conf = new ConfigureXMLReport(new File("out/reports/cucumber-reports/"));
        var conf = new ConfigureXMLReport(new File("out/reports/"));
        conf.addConfigureXMLMode(ConfigureXMLMode.ZIP_XML_RESULT_TO_FILE);
        conf.addConfigureXMLMode(ConfigureXMLMode.SEND_RESULT_TO_RP);
        GenerateXML appWithConf = new GenerateXML(conf);
        appWithConf.make();
    }
}
