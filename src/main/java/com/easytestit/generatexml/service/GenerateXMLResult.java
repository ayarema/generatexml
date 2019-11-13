package com.easytestit.generatexml.service;

import com.easytestit.generatexml.ValidationException;
import com.easytestit.generatexml.data.DefaultData;
import com.easytestit.generatexml.dto.feature.Feature;
import com.easytestit.generatexml.dto.aggregatedreport.AggregatedTestSuiteResult;
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

    public GenerateXMLResult convertObjectToXML(Feature feature) {
        try {
            LOGGER.info("Start to create XML file from Java Object ".concat(Feature.class.getName()));
            JAXBContext context = JAXBContext.newInstance(Feature.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(feature, System.out);

            m.marshal(feature, new File(DefaultData.reportResultsFolder.concat(DefaultData.fileName)));
            addNodesToXMLDocument(feature);
            LOGGER.info("File ".concat(DefaultData.fileName).concat(" was created"));
            return this;
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public void convertObjectToXML(AggregatedTestSuiteResult aggregatedTestSuiteResult) {
        try {
            LOGGER.info("Start to create XML file from Java Object ".concat(AggregatedTestSuiteResult.class.getName()));

            JAXBContext context = JAXBContext.newInstance(AggregatedTestSuiteResult.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(aggregatedTestSuiteResult, System.out);
            m.marshal(aggregatedTestSuiteResult, new File(DefaultData.reportResultsFolder.concat(DefaultData.fileName)));

        } catch (JAXBException e) {
            LOGGER.error(e.getMessage());
            throw new ValidationException(e);
        }
    }

    private void addNodesToXMLDocument(Feature feature) {
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
            addElement(existDoc, doc, feature);

        } catch (SAXException | ParserConfigurationException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void addElement(@NotNull Document existDoc, @NotNull Document doc, @NotNull Feature feature) {
        LOGGER.debug("Method addElement invoked");
        Element rootElement = doc.createElement("testsuite");
        rootElement.setAttribute("failures", "0");
        rootElement.setAttribute("name", feature.getName());
        rootElement.setAttribute("skipped","0");
        rootElement.setAttribute("tests","1");
        rootElement.setAttribute("time","2.241949");
        doc.appendChild(rootElement);

        Node copy = doc.importNode(existDoc.getDocumentElement(), true);
        rootElement.appendChild(copy);

        writeXMLtoFile(doc, filePath);
    }
}
