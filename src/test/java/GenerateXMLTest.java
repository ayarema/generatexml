import com.easytestit.generatexml.XMLReportApplication;
import com.easytestit.generatexml.configuration.Configuration;
import com.easytestit.generatexml.configuration.ConfigurationMode;
import org.junit.Test;

import java.io.File;

public class GenerateXMLTest {

    @Test
    public void generateXmlTest() {
        XMLReportApplication application = new XMLReportApplication();
        application.generateXMLreport();
    }

    @Test
    public void generateXmlTestWithConf() {
        var conf = new Configuration(new File("target/surefire-reports/"));
        conf.addConfigurationMode(ConfigurationMode.ZIP_XML_RESULT_TO_FILE);
        conf.addConfigurationMode(ConfigurationMode.SEND_RESULT_TO_RP);
        XMLReportApplication appWithConf = new XMLReportApplication(conf);
        appWithConf.generateXMLreport();
    }

    /*@Test
    public void generateXmlResult() {
        File file = new File("testXMLReport.xml");

        FeatureResult result = new FeatureResult();
        FeatureTestCaseResult caseResult = new FeatureTestCaseResult();

        FeatureCaseStepResult step = new FeatureCaseStepResult();

        result.setName("Smoke testing");
        result.setFailures("0");
        result.setTests("1");
        result.setTime("0.446274");
        result.setSkipped("SKIPPED");

        step.setSystemOut(Collections.singletonList("[STEP] ... ".concat("Given url 'https://jsonplaceholder.typicode.com/users' .................... passed")));

        caseResult.setTestName("create a user and then get it by id");
        caseResult.setTime("0.446274");
        caseResult.setCaseOutInfo(step);

        //result.setTestSuite("");
        result.setTestCases(Collections.singletonList(caseResult));

        try {
            System.out.println("Start to create XML file from Java Object ");
            JAXBContext context = JAXBContext.newInstance(FeatureResult.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(result, System.out);

            m.marshal(result, file);
        } catch (JAXBException e) {
            e.getMessage();
        }
    }*/
}
