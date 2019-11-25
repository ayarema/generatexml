package com.easytestit.generatexml.dto.output;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * If only a single testsuite element is present, the testsuites
 * element can be omitted. All attributes are optional.
 */
@NoArgsConstructor
@Setter
@XmlRootElement(name = "testsuites")
public class AggregatedTestSuiteResult {

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

    /**
     * Total number of failed tests from all testsuites
     */
    @XmlAttribute(name = "failures")
    private String failures;

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
    private Collection<AggregatedTestSuite> testSuites;

}
