package net.testiteasy.generatexml.service;

import net.testiteasy.generatexml.GenerateXMLReportException;
import net.testiteasy.generatexml.data.ConfigDataProvider;
import net.testiteasy.generatexml.dto.output.XMLReport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The service class {@link Serialization} which convert JAVA Object in XML file
 */
public class Serialization {

    private static final Logger LOGGER = Logger.getLogger(Serialization.class.getName());

    public Serialization() {

    }

    public void serializeToXML(final XMLReport xmlReport, final String projectName) {
        if (xmlReport != null ) {
            createResultsReportFolder();
            String fileName = projectName + ".xml";
            try {
                JAXBContext context = JAXBContext.newInstance(XMLReport.class);
                Marshaller m = context.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                m.marshal(xmlReport, new File(ConfigDataProvider.REPORT_RESULTS_FOLDER.concat(fileName)));
            } catch (JAXBException e) {
                throw new GenerateXMLReportException("Serialization to XML wasn't completed, because of: ");
            }
        } else {
            throw new GenerateXMLReportException("xmlReport should not be null but is null. See detailed stack trace: ");
        }
    }

    private void createResultsReportFolder() {
        File resultsFolder = new File(ConfigDataProvider.REPORT_RESULTS_FOLDER);
        LOGGER.log(Level.INFO, !resultsFolder.exists() && resultsFolder.mkdirs()
                ? "Report folder " + resultsFolder.getName() + " created successfully"
                : "Report folder " + resultsFolder.getName() + " was already created"
        );
    }
}
