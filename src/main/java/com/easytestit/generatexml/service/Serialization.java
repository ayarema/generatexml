package com.easytestit.generatexml.service;

import com.easytestit.generatexml.GenerateXMLReportException;
import com.easytestit.generatexml.data.ConfigDataProvider;
import com.easytestit.generatexml.dto.output.XMLReport;
import lombok.NonNull;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * The service class {@link Serialization} which convert JAVA Object in XML file
 */
public class Serialization {

    private static final Logger LOGGER = LogManager.getLogger(Serialization.class.getName());

    public Serialization() {
        LOGGER.log(Level.DEBUG, "XML service from Java Object to XML file invoked");
    }

    public void serializeToXML(@NonNull final XMLReport xmlReport) {
        try {
            LOGGER.info("Start to create XML file from Java Object ".concat(XMLReport.class.getName()));

            JAXBContext context = JAXBContext.newInstance(XMLReport.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(xmlReport,
                    new File(ConfigDataProvider.REPORT_RESULTS_FOLDER.concat(ConfigDataProvider.FILE_NAME))
            );
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
            throw new GenerateXMLReportException(e);
        }
    }
}
