package com.easytestit.generatexml.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TemporaryTestCase {

    private String keyword;
    private Integer line;
    private String name;
    private String description;
    private Long testDuration;

    private String testOutputString;

    public TemporaryTestCase setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public TemporaryTestCase setLine(Integer line) {
        this.line = line;
        return this;
    }

    public TemporaryTestCase setName(String name) {
        this.name = name;
        return this;
    }

    public TemporaryTestCase setTestDuration(Long testDuration) {
        this.testDuration = testDuration;
        return this;
    }

    public TemporaryTestCase setTestOutputString(String testOutputString) {
        this.testOutputString = testOutputString;
        return this;
    }

    public TemporaryTestCase setDescription(String description) {
        this.description = description;
        return this;
    }
}
