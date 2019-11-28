package com.easytestit.generatexml.dto.output;

import com.easytestit.generatexml.dto.output.properties.AggregatedProperties;
import com.easytestit.generatexml.dto.output.testcase.AggregatedTestCase;
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
public class TestSuite {

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
     * The total number of disabled tests in the suite
     */
    @XmlAttribute(name = "disabled")
    private String disabled;

    /**
     * The total number of tests in the suite that errored. An errored test is one that had an unanticipated problem,
     * for example an unchecked throwable; or a problem with the implementation of the test
     */
    @XmlAttribute(name = "errors")
    private String errors;

    /**
     * The total number of tests in the suite that failed. A failure is a test which the code has explicitly failed
     * by using the mechanisms for that purpose. e.g., via an assertEquals
     */
    @XmlAttribute(name = "failures")
    private String failures;

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
     * Specific properties for testsuite runs
     */
    @XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    private Collection<AggregatedProperties> aggregatedProperties;

    /**
     * Test cases, see deep into test case class
     */
    @XmlElement(name = "testcase")
    private Collection<AggregatedTestCase> testCases;

    /**
     * Test cases, see deep into test case class
     */
    @XmlElement(name = "tags")
    private String tags;

    /**
     * Data that was written to standard out while the test suite was executed
     */
    @XmlElement(name = "system-out")
    private String testSuitAggregatedOutInfo;

    /**
     * Data that was written to standard error while the test suite was executed
     */
    @XmlElement(name = "system-err")
    private String testSuitAggregatedOutErr;

    public TestSuite setName(String name) {
        this.name = name;
        return this;
    }

    public TestSuite setTests(String tests) {
        this.tests = tests;
        return this;
    }

    public TestSuite setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public TestSuite setErrors(String errors) {
        this.errors = errors;
        return this;
    }

    public TestSuite setFailures(String failures) {
        this.failures = failures;
        return this;
    }

    public TestSuite setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public TestSuite setId(String id) {
        this.id = id;
        return this;
    }

    public TestSuite setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public TestSuite setSkipped(String skipped) {
        this.skipped = skipped;
        return this;
    }

    public TestSuite setTime(String time) {
        this.time = time;
        return this;
    }

    public TestSuite setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TestSuite setAggregatedProperties(Collection<AggregatedProperties> aggregatedProperties) {
        this.aggregatedProperties = aggregatedProperties;
        return this;
    }

    public TestSuite setTestCases(Collection<AggregatedTestCase> testCases) {
        this.testCases = testCases;
        return this;
    }

    public TestSuite setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public TestSuite setTestSuitAggregatedOutInfo(String testSuitAggregatedOutInfo) {
        this.testSuitAggregatedOutInfo = testSuitAggregatedOutInfo;
        return this;
    }

    public TestSuite setTestSuitAggregatedOutErr(String testSuitAggregatedOutErr) {
        this.testSuitAggregatedOutErr = testSuitAggregatedOutErr;
        return this;
    }
}
