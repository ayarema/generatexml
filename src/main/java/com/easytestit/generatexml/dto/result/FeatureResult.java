package com.easytestit.generatexml.dto.result;

import com.easytestit.generatexml.dto.result.testcase.FeatureTestCaseResult;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "testsuite")
public class FeatureResult {

    private Collection<FeatureTestCaseResult> testCases;

    private String name;
    private String failures;
    private String tests;
    private String skipped;
    private String time;
    private Collection<String> tags;

    public FeatureResult() {

    }

    @XmlElement(name = "testcase")
    public Collection<FeatureTestCaseResult> getTestCases() {
        return testCases;
    }

    public void setTestCases(Collection<FeatureTestCaseResult> testCases) {
        this.testCases = testCases;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getFailures() {
        return failures;
    }

    @XmlAttribute(name = "failures")
    public void setFailures(String failures) {
        this.failures = failures;
    }

    public String getTests() {
        return tests;
    }

    @XmlAttribute(name = "tests")
    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getSkipped() {
        return skipped;
    }

    @XmlAttribute(name = "skipped")
    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }

    public String getTime() {
        return time;
    }

    @XmlAttribute(name = "time")
    public void setTime(String time) {
        this.time = time;
    }

    public Collection<String> getTags() {
        return tags;
    }

    @XmlAttribute(name = "tags")
    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }
}
