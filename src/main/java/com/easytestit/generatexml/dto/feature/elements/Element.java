package com.easytestit.generatexml.dto.feature.elements;

import com.google.gson.annotations.SerializedName;
import com.easytestit.generatexml.dto.feature.elements.steps.Step;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "element")
@XmlType(propOrder = {
        "line",
        "name",
        "description",
        "id",
        "type",
        "keyword",
        "steps"
})
public class Element {

    @SerializedName("line")
    private Integer line;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("keyword")
    private String keyword;

    @XmlElementWrapper(name = "steps")
    @XmlElement(name = "step")
    @SerializedName("steps")
    private List<Step> steps = null;

    @XmlElement(name = "line")
    private Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
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

    @XmlElement(name = "type")
    private String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "keyword")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @XmlElement(name = "id")
    private String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
