package com.easytestit.generatexml.dto.input.elements.steps;

import com.easytestit.generatexml.dto.input.elements.steps.docstring.DocString;
import com.google.gson.annotations.SerializedName;
import com.easytestit.generatexml.dto.input.elements.steps.match.Match;
import com.easytestit.generatexml.dto.input.elements.steps.result.Result;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Getter
public class Step {

    @NonNull
    @SerializedName("name")
    private String name;

    @NonNull
    @SerializedName("result")
    private Result result;

    @NonNull
    @SerializedName("match")
    private Match match;

    @NonNull
    @SerializedName("keyword")
    private String keyword;

    @NonNull
    @SerializedName("line")
    private Integer line;

    @NonNull
    @SerializedName("doc_string")
    private DocString docString;

}
