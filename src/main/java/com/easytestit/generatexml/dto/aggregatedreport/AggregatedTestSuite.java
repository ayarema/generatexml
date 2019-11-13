package com.easytestit.generatexml.dto.aggregatedreport;

import com.easytestit.generatexml.dto.aggregatedreport.properties.AggregatedProperties;
import com.easytestit.generatexml.dto.aggregatedreport.testcase.FeatureTestCaseResult;
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
public class AggregatedTestSuite {

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
    private Collection<FeatureTestCaseResult> testCases;

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

    public AggregatedTestSuite setName(String name) {
        this.name = name;
        return this;
    }

    public AggregatedTestSuite setTests(String tests) {
        this.tests = tests;
        return this;
    }

    public AggregatedTestSuite setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public AggregatedTestSuite setErrors(String errors) {
        this.errors = errors;
        return this;
    }

    public AggregatedTestSuite setFailures(String failures) {
        this.failures = failures;
        return this;
    }

    public AggregatedTestSuite setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public AggregatedTestSuite setId(String id) {
        this.id = id;
        return this;
    }

    public AggregatedTestSuite setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public AggregatedTestSuite setSkipped(String skipped) {
        this.skipped = skipped;
        return this;
    }

    public AggregatedTestSuite setTime(String time) {
        this.time = time;
        return this;
    }

    public AggregatedTestSuite setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public AggregatedTestSuite setAggregatedProperties(Collection<AggregatedProperties> aggregatedProperties) {
        this.aggregatedProperties = aggregatedProperties;
        return this;
    }

    public AggregatedTestSuite setTestCases(Collection<FeatureTestCaseResult> testCases) {
        this.testCases = testCases;
        return this;
    }

    public AggregatedTestSuite setTestSuitAggregatedOutInfo(String testSuitAggregatedOutInfo) {
        this.testSuitAggregatedOutInfo = testSuitAggregatedOutInfo;
        return this;
    }

    public AggregatedTestSuite setTestSuitAggregatedOutErr(String testSuitAggregatedOutErr) {
        this.testSuitAggregatedOutErr = testSuitAggregatedOutErr;
        return this;
    }
}
