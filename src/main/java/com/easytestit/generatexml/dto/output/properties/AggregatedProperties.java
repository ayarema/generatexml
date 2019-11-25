package com.easytestit.generatexml.dto.output.properties;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Properties (e.g., environment settings) set during test
 * execution. The properties element can appear 0 or once
 *
 * Property can appear multiple times. The name and value attributres are required
 */
@NoArgsConstructor
@NonNull
@Setter
@XmlRootElement(name = "property")
public class AggregatedProperties {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "value", required = true)
    private String value;
}
