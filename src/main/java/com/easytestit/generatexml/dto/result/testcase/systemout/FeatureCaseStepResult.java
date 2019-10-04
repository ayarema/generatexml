package com.easytestit.generatexml.dto.result.testcase.systemout;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "system-out")
public class FeatureCaseStepResult {

    private String systemOut;

    public FeatureCaseStepResult() {

    }

    public String getSystemOut() {
        return systemOut;
    }

    public void setSystemOut(String systemOut) {
        this.systemOut = systemOut;
    }
}
