import com.easytestit.generatexml.XMLReportApplication;
import com.easytestit.generatexml.configuration.Configuration;
import com.easytestit.generatexml.configuration.ConfigurationMode;
import com.easytestit.generatexml.dto.FeatureResult;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class GenerateXMLTest {

    @Test
    public void generateXmlTest() {
        XMLReportApplication application = new XMLReportApplication();
        application.generateXMLreport();
    }

    @Test
    public void generateXmlTestWithConf() {
        Configuration conf = new Configuration(new File("target/surefire-reports/"));
        conf.addConfigurationMode(ConfigurationMode.ZIP_XML_RESULT_FILE);
        conf.addConfigurationMode(ConfigurationMode.SEND_RESULT_TO_REPORT_PORTAL);
        XMLReportApplication appWithConf = new XMLReportApplication(conf);
        appWithConf.generateXMLreport();
    }

    @Test
    public void generateXmlResult() {
        String resultString = "Given url 'https://jsonplaceholder.typicode.com/users' .................... passed";
        FeatureResult result = new FeatureResult();
        result.setStepsInfo(resultString);
        result.setTestSuite("test suit for all test cases");
        result.setTestCases("first test cases");
        try {
            System.out.println("Start to create XML file from Java Object ");
            JAXBContext context = JAXBContext.newInstance(FeatureResult.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(result, System.out);

            m.marshal(result, new File("testXMLReport.xml"));
        } catch (JAXBException e) {
            e.getMessage();
        }
    }
}
