package com.easytestit.generatexml.service;

import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.configuration.ConfigureXMLReport;
import com.easytestit.generatexml.data.ConfigDataProvider;
import com.easytestit.generatexml.dto.output.XMLReport;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * The service class {@link Serialization} which convert JAVA Object in XML file
 */
public class Serialization {

    public Serialization() {

    }

    public void serializeToXML(final XMLReport xmlReport, final String projectName, ConfigureXMLReport configuration) {
        if (xmlReport != null ) {
            String fileName = projectName + ".xml";
            try {
                JAXBContext context = JAXBContext.newInstance(XMLReport.class);
                Marshaller m = context.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                if (configuration == null) {
                    m.marshal(xmlReport, new File(ConfigDataProvider.DEFAULT_FOLDER.concat(fileName)));
                } else {
                    m.marshal(xmlReport, new File(ConfigDataProvider.REPORT_RESULTS_FOLDER.concat(fileName)));
                }
            } catch (JAXBException e) {
                throw new GenerateXMLReportException("Serialization to XML wasn't completed, because of: ");
            }
        } else {
            throw new GenerateXMLReportException("xmlReport should not be null but is null. See detailed stack trace: ");
        }
    }
}
