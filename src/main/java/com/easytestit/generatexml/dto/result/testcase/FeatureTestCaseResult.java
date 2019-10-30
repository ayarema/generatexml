package com.easytestit.generatexml.dto.result.testcase;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "testcase")
public class FeatureTestCaseResult {

    @XmlElement(name = "system-out")
    private String caseOutInfo;

    @XmlAttribute(name = "name")
    private String testName;

    @XmlAttribute(name = "time")
    private String time;

    @XmlAttribute(name = "description")
    private String description;

    public FeatureTestCaseResult() {

    }

    public FeatureTestCaseResult setTestName(String testName) {
        this.testName = testName;
        return this;
    }

    public FeatureTestCaseResult setDescription(String description) {
        this.description = description;
        return this;
    }

    public FeatureTestCaseResult setCaseOutInfo(String caseOutInfo) {
        this.caseOutInfo = caseOutInfo;
        return this;
    }
}
