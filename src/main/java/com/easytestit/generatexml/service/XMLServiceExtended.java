package com.easytestit.generatexml.service;

import com.easytestit.generatexml.dto.output.ReportSuites;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public abstract class XMLServiceExtended implements XMLService {

    @Contract(pure = true)
    XMLServiceExtended() {

    }

    private static final Logger LOGGER = LogManager.getLogger(XMLServiceExtended.class.getName());

    @Override
    public void writeXMLtoFile(@NotNull Document doc, String filePath) {
        try {
            LOGGER.debug("Method writeXMLtoFile invoked");
            doc.getDocumentElement().normalize();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);

            LOGGER.info("XML file updated successfully");
        } catch (TransformerFactoryConfigurationError | TransformerException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void updateElementValue(Document doc) {
        LOGGER.debug("Method updateElementValue invoked");
        NodeList users = doc.getElementsByTagName("User");
        Element user;
        // loop for each user
        for (int i = 0; i < users.getLength(); i++) {
            user = (Element) users.item(i);
            Node name = user.getElementsByTagName("firstName").item(0).getFirstChild();
            name.setNodeValue(name.getNodeValue().toUpperCase());
        }
    }

    @Override
    public void addElement(Document doc) {
        LOGGER.debug("Method addElement invoked");
        NodeList users = doc.getElementsByTagName("User");
        Element emp;

        // loop for each user
        for (int i = 0; i < users.getLength(); i++) {
            emp = (Element) users.item(i);
            Element salaryElement = doc.createElement("salary");
            salaryElement.appendChild(doc.createTextNode("10000"));
            emp.appendChild(salaryElement);
        }
    }

    @Override
    public void deleteElement(Document doc) {
        LOGGER.debug("Method deleteElement invoked");
        NodeList users = doc.getElementsByTagName("User");
        Element user;
        // loop for each user
        for (int i = 0; i < users.getLength(); i++) {
            user = (Element) users.item(i);
            Node genderNode = user.getElementsByTagName("gender").item(0);
            user.removeChild(genderNode);
        }
    }

    public abstract void convertObjectToXML(ReportSuites reportSuites);
}
