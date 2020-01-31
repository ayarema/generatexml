package com.easytestit.generatexml.dto.input.elements.steps;

import com.easytestit.generatexml.dto.input.elements.steps.docstring.DocString;
import com.easytestit.generatexml.dto.input.elements.steps.match.Match;
import com.easytestit.generatexml.dto.input.elements.steps.result.Result;

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
