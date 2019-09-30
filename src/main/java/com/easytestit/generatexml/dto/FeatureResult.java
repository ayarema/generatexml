package com.easytestit.generatexml.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeatureResult {

    private String testSuite;
    private String testCases;
    private String stepsInfo;
    private String failures;
    private String tests;
    private String time;

    public FeatureResult() {

    }

    @XmlElementWrapper(name = "testcase")
    @XmlElement(name = "testsuite")
    public String getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(String testSuite) {
        this.testSuite = testSuite;
    }

    @XmlElementWrapper(name = "step-out")
    @XmlElement(name = "testcase")
    public String getTestCases() {
        return testCases;
    }

    public void setTestCases(String testCases) {
        this.testCases = testCases;
    }

    @XmlElement(name = "step-out")
    public String getStepsInfo() {
        return stepsInfo;
    }

    public void setStepsInfo(String stepsInfo) {
        this.stepsInfo = stepsInfo;
    }

    @Override
    public String toString() {
        return "ReportDocumentResult{"
                .concat("testSuite='").concat(testSuite).concat("'").concat(" ")
                .concat("testCase='").concat(testCases).concat("'").concat(" ")
                .concat("stepOut='").concat(stepsInfo).concat("'").concat(" ")
                .concat("}");
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

    public String getTime() {
        return time;
    }

    @XmlAttribute(name = "time")
    public void setTime(String time) {
        this.time = time;
    }
}
