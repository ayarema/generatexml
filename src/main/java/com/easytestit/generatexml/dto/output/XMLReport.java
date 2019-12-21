package com.easytestit.generatexml.dto.output;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * If only a single testsuite element is present, the testsuites
 * element can be omitted. All attributes are optional.
 */
@NoArgsConstructor
@XmlRootElement(name = "testsuites")
public class XMLReport {

    /**
     * Total number of failed tests from all testsuites
     */
    @XmlAttribute(name = "failures")
    private String failures;

    /**
     * Total number of disabled tests from all testsuites
     */
    @XmlAttribute(name = "disabled")
    private String disabled;

    /**
     * Total number of tests with error result from all testsuites
     */
    @XmlAttribute(name = "errors")
    private String errors;

    @XmlAttribute(name = "name")
    private String name;

    /**
     * Total number of successful tests from all testsuites
     */
    @XmlAttribute(name = "tests")
    private String tests;

    /**
     * Time in seconds to execute all test suites
     */
    @XmlAttribute(name = "time")
    private String time;

    /**
     * Child testsuite element, if the report has quantity runs more than 1
     */
    @XmlElement(name = "testsuite")
    private Collection<SingleReportSuite> singleReportSuites;

    public XMLReport setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public XMLReport setErrors(String errors) {
        this.errors = errors;
        return this;
    }

    public XMLReport setFailures(String failures) {
        this.failures = failures;
        return this;
    }

    public XMLReport setName(String name) {
        this.name = name;
        return this;
    }

    public XMLReport setTests(String tests) {
        this.tests = tests;
        return this;
    }

    public XMLReport setTime(String time) {
        this.time = time;
        return this;
    }

    public XMLReport setSingleReportSuites(Collection<SingleReportSuite> singleReportSuites) {
        this.singleReportSuites = singleReportSuites;
        return this;
    }
}
