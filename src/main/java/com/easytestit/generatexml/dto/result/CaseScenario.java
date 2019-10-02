package com.easytestit.generatexml.dto.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "scenario")
public class CaseScenario {

    private Collection<CaseStep> stepsInfo;

    @XmlElement(name = "step")
    public Collection<CaseStep> getStepsInfo() {
        return stepsInfo;
    }

    public void setStepsInfo(Collection<CaseStep> stepsInfo) {
        this.stepsInfo = stepsInfo;
    }
}
