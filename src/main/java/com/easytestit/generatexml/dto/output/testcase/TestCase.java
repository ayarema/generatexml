package com.easytestit.generatexml.dto.output.testcase;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Testcase can appear multiple times, see /testsuites/testsuite@tests
 */
@XmlRootElement(name = "testcase")
public class TestCase {

    public TestCase() {

    }

    /**
     * Name of the test method
     */
    @XmlAttribute(name = "name", required = true)
    private String testName;

    /**
     * Number of assertions in the test case
     */
    @XmlAttribute(name = "assertions")
    private String assertionsMessage;

    /**
     * Name of the test method
     */
    @XmlAttribute(name = "classname", required = true)
    private String className;

    @XmlAttribute(name = "status")
    private String status;

    /**
     * Time taken (in seconds) to execute the test
     */
    @XmlAttribute(name = "time")
    private String time;

    @XmlAttribute(name = "description")
    private String description;

    /**
     * Data that was written to standard error while the test was executed
     */
    @XmlElement(name = "system-err")
    private String caseOutErr;

    /**
     * Data that was written to standard out while the test was executed
     */
    @XmlElement(name = "system-out")
    private String caseOutInfo;

    public TestCase setStatus(String status) {
        this.status = status;
        return this;
    }

    public TestCase setTestName(String testName) {
        this.testName = testName;
        return this;
    }

    public TestCase setDescription(String description) {
        this.description = description;
        return this;
    }

    public TestCase setCaseOutErr(String caseOutErr) {
        this.caseOutErr = caseOutErr;
        return this;
    }

    public TestCase setCaseOutInfo(String caseOutInfo) {
        this.caseOutInfo = caseOutInfo;
        return this;
    }
}
