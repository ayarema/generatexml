package net.testiteasy.generatexml.dto.input.elements;

import net.testiteasy.generatexml.dto.input.tags.Tag;
import net.testiteasy.generatexml.dto.input.elements.steps.Step;

import java.util.List;

/**
 * This object describes one of the main parts of the contract model
 * of the input JSON file data.
 */
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
