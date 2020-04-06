package net.testiteasy.generatexml.dto.output.properties;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Properties (e.g., environment settings) set during test
 * execution. The properties element can appear 0 or once
 *
 * Property can appear multiple times. The name and value attributes are required
 */
@XmlRootElement(name = "property")
public class SuiteProperties {

    public SuiteProperties() {

    }

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "value", required = true)
    private String value;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
