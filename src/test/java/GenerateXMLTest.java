import com.iaremenko.generatexml.GenerateXMLApplication;
import com.iaremenko.generatexml.configuration.Configuration;
import org.junit.Test;

import java.io.File;

public class GenerateXMLTest {

    private GenerateXMLApplication application = new GenerateXMLApplication();

    @Test
    public void generateXmlTest() {
        application.generateXML().sendXML();
    }

    @Test
    public void generateXmlTestWithConf() {
        Configuration conf = new Configuration(new File("target/surefire-reports/"));
        GenerateXMLApplication appWithConf = new GenerateXMLApplication(conf);
        appWithConf.generateXML().sendXML();
    }
}
