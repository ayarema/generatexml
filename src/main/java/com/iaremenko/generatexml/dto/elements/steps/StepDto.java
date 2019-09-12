package com.iaremenko.generatexml.dto.elements.steps;

import com.google.gson.annotations.SerializedName;
import com.iaremenko.generatexml.dto.elements.steps.docstrings.DocStringDto;
import com.iaremenko.generatexml.dto.elements.steps.matches.MatchDto;
import com.iaremenko.generatexml.dto.elements.steps.results.ResultDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "step")
@XmlType(propOrder = {
        "name",
        "result",
        "match",
        "keyword",
        "line",
        "docString"
})
public class StepDto {

    @SerializedName("name")
    private String name;

    @SerializedName("result")
    private ResultDto result;

    @SerializedName("match")
    private MatchDto match;

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("line")
    private Integer line;

    @SerializedName("doc_string")
    private DocStringDto docString;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "result")
    private ResultDto getResult() {
        return result;
    }

    public void setResult(ResultDto result) {
        this.result = result;
    }

    @XmlElement(name = "match")
    private MatchDto getMatch() {
        return match;
    }

    public void setMatch(MatchDto match) {
        this.match = match;
    }

    @XmlElement(name = "keyword")
    private String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @XmlElement(name = "line")
    private Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    @XmlElement(name = "doc_string")
    private DocStringDto getDocString() {
        return docString;
    }

    public void setDocString(DocStringDto docString) {
        this.docString = docString;
    }
}
