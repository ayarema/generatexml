package com.easytestit.generatexml.dto.feature.elements.steps;

import com.easytestit.generatexml.dto.feature.elements.steps.docstring.DocString;
import com.google.gson.annotations.SerializedName;
import com.easytestit.generatexml.dto.feature.elements.steps.match.Match;
import com.easytestit.generatexml.dto.feature.elements.steps.result.Result;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@NonNull
@Getter
public class Step {

    @SerializedName("name")
    private String name;

    @SerializedName("result")
    private Result result;

    @SerializedName("match")
    private Match match;

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("line")
    private Integer line;

    @SerializedName("doc_string")
    private DocString docString;

}
