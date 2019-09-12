package com.iaremenko.generatexml.dto.elements.steps.matches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "match")
public class MatchDto {

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("arguments")
    @Expose
    private List<Object> arguments = null;

    @XmlElement(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlElement(name = "arguments")
    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }
}
