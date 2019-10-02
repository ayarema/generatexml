package com.easytestit.generatexml.dto.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "background")
public class CaseBackground {

    private String background;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
