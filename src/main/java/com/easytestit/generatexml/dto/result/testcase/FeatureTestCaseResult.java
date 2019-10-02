package com.easytestit.generatexml.dto.result.testcase;

import com.easytestit.generatexml.dto.result.CaseBackground;
import com.easytestit.generatexml.dto.result.CaseScenario;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

@XmlRootElement(name = "testcase")
@XmlType(propOrder = {
        "stepsBackground",
        "caseScenarioCollections"
})
public class FeatureTestCaseResult {

    private Collection<CaseBackground> stepsBackground;
    private Collection<CaseScenario> caseScenarioCollections = null;

    private String testName;
    private String time;

    @XmlElement(name = "background")
    public Collection<CaseBackground> getStepsBackground() {
        return stepsBackground;
    }

    public void setStepsBackground(Collection<CaseBackground> stepsBackground) {
        this.stepsBackground = stepsBackground;
    }

    @XmlElement(name = "scenario")
    public Collection<CaseScenario> getCaseScenarioCollections() {
        return caseScenarioCollections;
    }

    public void setCaseScenarioCollections(Collection<CaseScenario> caseScenarioCollections) {
        this.caseScenarioCollections = caseScenarioCollections;
    }

    public String getTestName() {
        return testName;
    }

    @XmlAttribute(name = "testname")
    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTime() {
        return time;
    }

    @XmlAttribute(name = "time")
    public void setTime(String time) {
        this.time = time;
    }
}
