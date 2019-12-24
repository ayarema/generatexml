import com.easytestit.generatexml.GenerateXML;
import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import org.junit.Test;

public class GenerateXMLTest {

    private final String PATH = "out/reports/cucumber-reports/";

    @Test
    public void generateXmlTestWithConf() {
        //var conf = new ConfigureXMLReport(new File("out/reports/cucumber-reports/"));
        var conf = new ConfigureXMLReport(
                PATH,
                true,
                true
        );
        new GenerateXML(conf).make();
    }
}
