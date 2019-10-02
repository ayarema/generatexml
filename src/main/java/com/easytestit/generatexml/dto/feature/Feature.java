package com.easytestit.generatexml.dto.feature;

import com.easytestit.generatexml.dto.feature.elements.Element;
import com.easytestit.generatexml.dto.feature.tags.Tag;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * The root class which described logic of storing data which stored in JSON file and need it to XML file
 * Moreover, it's a class which is a start point for test JSON report data
 */
@XmlRootElement(namespace = "document")
@XmlType(propOrder = {
        "line",
        "elements",
        "name",
        "description",
        "id",
        "keyword",
        "uri",
        "tags"
})
public class Feature {

    @SerializedName("line")
    private Integer line;

    @XmlElementWrapper(name = "elements")
    @XmlElement(name = "element")
    @SerializedName("elements")
    private List<Element> elements = null;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("uri")
    private String uri;

    @SerializedName("tags")
    private List<Tag> tags = null;

    @XmlElement(name = "line")
    private Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "description")
    private String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "id")
    private String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "keyword")
    private String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @XmlElement(name = "uri")
    private String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlElement(name = "tags")
    private List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
