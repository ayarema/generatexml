package net.testiteasy.generatexml.dto.input.elements.steps;

import net.testiteasy.generatexml.dto.input.elements.steps.docstring.DocString;
import net.testiteasy.generatexml.dto.input.elements.steps.match.Match;
import net.testiteasy.generatexml.dto.input.elements.steps.result.Result;
import net.testiteasy.generatexml.dto.input.elements.Element;

/**
 * This object describes the contract model of the JSON file,
 * which is an embedded part of the {@link Element} file.
 *
 * This part is always a nesting in, the so-called test case, for example, Background or Scenario.
 */
public class Step {

    public Step() {

    }

    private String name;

    private Result result;

    private Match match;

    private String keyword;

    private Integer line;

    private DocString doc_string;

    public String getName() {
        return name;
    }

    public Result getResult() {
        return result;
    }

    public Match getMatch() {
        return match;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getLine() {
        return line;
    }

    public DocString getDoc_string() {
        return doc_string;
    }
}
