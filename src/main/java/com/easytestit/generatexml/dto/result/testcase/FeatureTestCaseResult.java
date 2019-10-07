package com.easytestit.generatexml.dto.result.testcase;

import com.easytestit.generatexml.dto.result.testcase.systemout.FeatureCaseStepResult;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "testcase")
public class FeatureTestCaseResult {

    private Collection<FeatureCaseStepResult> caseOutInfo;

    private String testName;
    private String time;
    private String description;

    public FeatureTestCaseResult() {

    }

    @XmlElement(name = "system-out")
    public Collection<FeatureCaseStepResult> getCaseOutInfo() {
        return caseOutInfo;
    }

    public void setCaseOutInfo(Collection<FeatureCaseStepResult> caseOutInfo) {
        this.caseOutInfo = caseOutInfo;
    }

    public String getTestName() {
        return testName;
    }

    @XmlAttribute(name = "name")
    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTime() {
        return time;
    }

    @XmlAttribute(name = "time")
    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    @XmlAttribute(name = "description")
    public void setDescription(String description) {
        this.description = description;
    }
}
