package com.easytestit.generatexml.dto.elements.steps.docstrings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "doc_string")
public class DocString {

    @SerializedName("content_type")
    @Expose
    private String contentType;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("line")
    @Expose
    private Integer line;

    @XmlElement(name = "content_type")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @XmlElement(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement(name = "line")
    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }
}
