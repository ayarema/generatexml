package com.iaremenko.generatexml.service;

import com.iaremenko.generatexml.data.DefaultData;
import com.iaremenko.generatexml.dto.ReportDocument;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * The service class @link DocumentXMLFilling which convert JAVA Object in XML file
 */
public class GenerateXMLResult extends XMLServiceExtended {

    private static final Logger LOGGER = LogManager.getLogger(GenerateXMLResult.class.getName());
    private String filePath = DefaultData.reportResultsFolder.concat(DefaultData.fileName);

    public GenerateXMLResult() {
        LOGGER.log(Level.DEBUG, "Generate XML file from Java Object invoked");
    }

    @Override
    public GenerateXMLResult convertObjectToXML(ReportDocument reportDocument) {
        try {
            LOGGER.info("Start to create XML file from Java Object ".concat(ReportDocument.class.getName()));
            JAXBContext context = JAXBContext.newInstance(ReportDocument.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(reportDocument, System.out);

            m.marshal(reportDocument, new File(DefaultData.reportResultsFolder.concat(DefaultData.fileName)));
            addNodesToXMLDocument(reportDocument);
            LOGGER.info("File ".concat(DefaultData.fileName).concat(" was created"));
            return this;
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private void addNodesToXMLDocument(ReportDocument reportDocument) {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            // parse xml file and load into document
            Document existDoc = dBuilder.parse(xmlFile);
            //existDoc.getDocumentElement().normalize();
            Document doc = dBuilder.newDocument();

            // add new element
            addElement(existDoc, doc, reportDocument);

        } catch (SAXException | ParserConfigurationException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void addElement(@NotNull Document existDoc, Document doc, ReportDocument reportDocument) {
        LOGGER.debug("Method addElement invoked");
        Element rootElement = doc.createElement("testsuite");
        rootElement.setAttribute("failures", "0");
        rootElement.setAttribute("name",reportDocument.getName());
        rootElement.setAttribute("skipped","0");
        rootElement.setAttribute("tests","1");
        rootElement.setAttribute("time","2.241949");
        doc.appendChild(rootElement);

        Node copy = doc.importNode(existDoc.getDocumentElement(), true);
        rootElement.appendChild(copy);

        writeXMLtoFile(doc, filePath);
    }
}
