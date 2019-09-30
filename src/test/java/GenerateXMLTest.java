import com.iaremenko.generatexml.XMLReportApplication;
import com.iaremenko.generatexml.configuration.Configuration;
import com.iaremenko.generatexml.configuration.ConfigurationMode;
import com.iaremenko.generatexml.data.DefaultData;
import com.iaremenko.generatexml.dto.ReportDocument;
import com.iaremenko.generatexml.dto.ReportDocumentResult;
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
        ReportDocumentResult result = new ReportDocumentResult();
        result.setStepsInfo(resultString);
        result.setTestSuite("test suit for all test cases");
        result.setTestCases("first test cases");
        try {
            System.out.println("Start to create XML file from Java Object ");
            JAXBContext context = JAXBContext.newInstance(ReportDocumentResult.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(result, System.out);

            m.marshal(result, new File("testXMLReport.xml"));
        } catch (JAXBException e) {
            e.getMessage();
        }
    }
}
