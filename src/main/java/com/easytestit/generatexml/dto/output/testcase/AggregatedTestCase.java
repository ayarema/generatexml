package com.easytestit.generatexml.dto.output.testcase;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Testcase can appear multiple times, see /testsuites/testsuite@tests
 */
@NoArgsConstructor
@NonNull
@XmlRootElement(name = "testcase")
public class AggregatedTestCase {

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
     * Data that was written to standard out while the test was executed
     */
    @XmlElement(name = "system-out")
    private String caseOutInfo;

    /**
     * Data that was written to standard error while the test was executed
     */
    @XmlElement(name = "system-err")
    private String caseOutErr;

    public AggregatedTestCase setTestName(String testName) {
        this.testName = testName;
        return this;
    }

    public AggregatedTestCase setDescription(String description) {
        this.description = description;
        return this;
    }

    public AggregatedTestCase setCaseOutInfo(String caseOutInfo) {
        this.caseOutInfo = caseOutInfo;
        return this;
    }

    public AggregatedTestCase setCaseOutErr(String caseOutErr) {
        this.caseOutErr = caseOutErr;
        return this;
    }
}
