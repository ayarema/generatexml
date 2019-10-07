package com.easytestit.generatexml.dto.feature.elements.steps;

import com.easytestit.generatexml.dto.feature.elements.steps.docstrings.DocString;
import com.google.gson.annotations.SerializedName;
import com.easytestit.generatexml.dto.feature.elements.steps.matches.Match;
import com.easytestit.generatexml.dto.feature.elements.steps.results.Result;

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
public class Step {

    @SerializedName("name")
    private String name;

    @SerializedName("result")
    private Result result;

    @SerializedName("match")
    private Match match;

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("line")
    private Integer line;

    @SerializedName("doc_string")
    private DocString docString;

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "result")
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @XmlElement(name = "match")
    private Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @XmlElement(name = "keyword")
    public String getKeyword() {
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
    private DocString getDocString() {
        return docString;
    }

    public void setDocString(DocString docString) {
        this.docString = docString;
    }
}
