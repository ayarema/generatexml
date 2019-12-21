package com.easytestit.generatexml.dto.output;

import com.easytestit.generatexml.dto.output.properties.SuiteProperties;
import com.easytestit.generatexml.dto.output.testcase.TestCase;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * Testsuite can appear multiple times, if contained in a testsuites element.
 * It can also be the root element
 */
@NoArgsConstructor
@NonNull
@XmlRootElement(name = "testsuite")
public class SingleReportSuite {

    /**
     * Full (class) name of the test for non-aggregated testsuite documents.
     * Class name without the package for aggregated testsuites documents.
     */
    @XmlAttribute(name = "name", required = true)
    private String name;

    /**
     * The total number of tests in the suite
     */
    @XmlAttribute(name = "tests", required = true)
    private String tests;

    /**
     * The status for a feature file which accumulates all statuses.
     * Even if one of the steps in one feature file has failed status, the scenario will have status failed.
     */
    @XmlAttribute(name = "status")
    private String status;

    /**
     * The total number of tests in the suite that failed. A failure is a test which the code has explicitly failed
     * by using the mechanisms for that purpose. e.g., via an assertEquals
     */
    @XmlAttribute(name = "failures")
    private String failures;

    /**
     * The total number of tests in the suite that errored. An errored test is one that had an unanticipated problem,
     * for example an unchecked throwable; or a problem with the implementation of the test
     */
    @XmlAttribute(name = "errors")
    private String errors;

    /**
     * The total number of disabled tests in the suite
     */
    @XmlAttribute(name = "disabled")
    private String disabled;

    /**
     * Host on which the tests were executed. 'localhost' should be used if the hostname cannot be determined
     */
    @XmlAttribute(name = "hostname")
    private String hostname;

    /**
     * Starts at 0 for the first testsuite and is incremented by 1 for each following testsuite
     */
    @XmlAttribute(name = "id")
    private String id;

    /**
     * Derived from testsuite/@name in the non-aggregated documents
     */
    @XmlAttribute(name = "package")
    private String packageName;

    /**
     * The total number of skipped tests
     */
    @XmlAttribute(name = "skipped")
    private String skipped;

    /**
     * Time taken (in seconds) to execute the tests in the suite
     */
    @XmlAttribute(name = "time")
    private String time;

    /**
     * When the test was executed in ISO 8601 format (2014-01-21T16:17:18). Timezone may not be specified
     */
    @XmlAttribute(name = "timestamp")
    private String timestamp;

    /**
     * Tags which was defined for each feature file
     */
    @XmlAttribute(name = "tags")
    private String tags;

    /**
     * Specific properties for testsuite runs
     */
    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    private Collection<SuiteProperties> suiteProperties;

    /**
     * Test cases, see deep into test case class
     */
    @XmlElement(name = "testcase")
    private Collection<TestCase> testCases;

    /**
     * Data that was written to standard out while the test suite was executed
     */
    @XmlElement(name = "system-out")
    private String testOutInfo;

    /**
     * Data that was written to standard error while the test suite was executed
     */
    @XmlElement(name = "failure")
    private String testOutErr;

    /**
     * The message which contains short description of failure step
     */
    @XmlAttribute(name = "message")
    private String message;

    public SingleReportSuite setName(String name) {
        this.name = name;
        return this;
    }

    public SingleReportSuite setTests(String tests) {
        this.tests = tests;
        return this;
    }

    public SingleReportSuite setStatus(String status) {
        this.status = status;
        return this;
    }

    public SingleReportSuite setFailures(String failures) {
        this.failures = failures;
        return this;
    }

    public SingleReportSuite setErrors(String errors) {
        this.errors = errors;
        return this;
    }

    public SingleReportSuite setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public SingleReportSuite setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public SingleReportSuite setId(String id) {
        this.id = id;
        return this;
    }

    public SingleReportSuite setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public SingleReportSuite setSkipped(String skipped) {
        this.skipped = skipped;
        return this;
    }

    public SingleReportSuite setTime(String time) {
        this.time = time;
        return this;
    }

    public SingleReportSuite setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public SingleReportSuite setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public SingleReportSuite setSuiteProperties(Collection<SuiteProperties> suiteProperties) {
        this.suiteProperties = suiteProperties;
        return this;
    }

    public SingleReportSuite setTestCases(Collection<TestCase> testCases) {
        this.testCases = testCases;
        return this;
    }

    public SingleReportSuite setTestOutInfo(String testOutInfo) {
        this.testOutInfo = testOutInfo;
        return this;
    }

    public SingleReportSuite setTestOutErr(String testOutErr) {
        this.testOutErr = testOutErr;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
