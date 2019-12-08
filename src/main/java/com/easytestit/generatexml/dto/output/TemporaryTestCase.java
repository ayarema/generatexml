package com.easytestit.generatexml.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TemporaryTestCase {

    private String parentKeyword;
    private Integer parentLine;
    private String parentName;
    private Long testDuration;

    private String testOutputString;

    public TemporaryTestCase setParentKeyword(String parentKeyword) {
        this.parentKeyword = parentKeyword;
        return this;
    }

    public TemporaryTestCase setParentLine(Integer parentLine) {
        this.parentLine = parentLine;
        return this;
    }

    public TemporaryTestCase setParentName(String parentName) {
        this.parentName = parentName;
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
}
