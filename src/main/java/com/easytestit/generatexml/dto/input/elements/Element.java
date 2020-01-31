package com.easytestit.generatexml.dto.input.elements;

import com.easytestit.generatexml.dto.input.tags.Tag;
import com.easytestit.generatexml.dto.input.elements.steps.Step;

import java.util.List;

public class Element {

    public Element() {

    }

    private Integer line;

    private String name;

    private String description;

    private String id;

    private String type;

    private String keyword;

    private List<Step> steps;

    private List<Tag> tags;

    public Integer getLine() {
        return line;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
