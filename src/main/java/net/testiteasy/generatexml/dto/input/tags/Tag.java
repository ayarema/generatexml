package net.testiteasy.generatexml.dto.input.tags;

/**
 * The part of the feature's input JSON file which is the data contract and
 * participate in parsing into Java Object.
 *
 * This part appears in the root feature's input JSON file and also could be appeared in each test case,
 * such as Background and Scenario as well.
 */
public class Tag {

    public Tag() {

    }

    private String name;

    private Integer line;

    public String getName() {
        return name;
    }

    public Integer getLine() {
        return line;
    }
}
