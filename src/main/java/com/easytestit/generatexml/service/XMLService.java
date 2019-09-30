package com.easytestit.generatexml.service;

import org.w3c.dom.Document;

public interface XMLService {

    /**
     * Write something to XML document.
     * @param doc XML document in which will write text by specific logic
     * @param filePath path to file in which will write data
     */
    void writeXMLtoFile(Document doc, String filePath);

    /**
     * Update element value in XML document.
     * @param doc XML document in which will updated by specific logic specific node
     */
    void updateElementValue(Document doc);

    /**
     * Add a new element node or value to existing node.
     * @param doc XML document in which will added by specific logic
     */
    void addElement(Document doc);

    /**
     * Delete gender element from specific element
     * @param doc XML document in which will deleted by specific logic specific nodes
     */
    void deleteElement(Document doc);

}
