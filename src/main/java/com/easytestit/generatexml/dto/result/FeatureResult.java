package com.easytestit.generatexml.dto.result;

import com.easytestit.generatexml.dto.result.testcase.FeatureTestCaseResult;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "testsuite")
public class FeatureResult {

    @XmlElement(name = "testcase")
    private Collection<FeatureTestCaseResult> testCases;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "failures")
    private String failures;

    @XmlAttribute(name = "tests")
    private String tests;

    @XmlAttribute(name = "skipped")
    private String skipped;

    @XmlAttribute(name = "time")
    private String time;

    @XmlAttribute(name = "tags")
    private String tags;

    public FeatureResult() {

    }

    public void setTestCases(Collection<FeatureTestCaseResult> testCases) {
        this.testCases = testCases;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFailures(String failures) {
        this.failures = failures;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
