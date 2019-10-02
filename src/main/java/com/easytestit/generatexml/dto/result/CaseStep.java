package com.easytestit.generatexml.dto.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "step")
public class CaseStep {

    private String stepInfo;

    public String getStepInfo() {
        return stepInfo;
    }

    public void setStepInfo(String stepInfo) {
        this.stepInfo = stepInfo;
    }
}
