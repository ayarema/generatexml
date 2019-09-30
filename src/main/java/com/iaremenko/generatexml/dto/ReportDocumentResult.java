package com.iaremenko.generatexml.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class ReportDocumentResult {

    @XmlElementWrapper(name = "testcase")
    private String testSuite;

    @XmlElementWrapper(name = "step-out")
    private String testCases;

    private String stepsInfo;

    @XmlElement(name = "testsuite")
    public String getTestSuite() {
        return testSuite;
    }

    public void setTestSuite(String testSuite) {
        this.testSuite = testSuite;
    }

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

    /*@Override
    public String toString() {
        return "ReportDocumentResult{"
                .concat("testSuite='").concat(testSuite).concat("'").concat(" ")
                .concat("testCase='").concat(testCases).concat("'").concat(" ")
                .concat("stepOut='").concat(stepsInfo).concat("'").concat(" ")
                .concat("}");
    }*/
}
