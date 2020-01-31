package com.easytestit.generatexml.dto.input;

import com.easytestit.generatexml.dto.input.elements.Element;
import com.easytestit.generatexml.dto.input.tags.Tag;

import java.util.List;

/**
 * The root class which described logic of storing data which stored in JSON file and need it to XML file
 * Moreover, it's a class which is a start point for test JSON report data
 */
public class Feature {

    public Feature() {

    }

    private Integer line;

    private List<Element> elements;

    private String name;

    private String description;

    private String id;

    private String keyword;

    private String uri;

    private List<Tag> tags;

    public Integer getLine() {
        return line;
    }

    public List<Element> getElements() {
        return elements;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getUri() {
        return uri;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
