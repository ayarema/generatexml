package com.easytestit.generatexml.dto.input.elements.steps.docstring;

import com.google.gson.annotations.SerializedName;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Getter;

@NoArgsConstructor
@Getter
public class DocString {

    @NonNull
    @SerializedName("content_type")
    private String contentType;

    @NonNull
    @SerializedName("value")
    private String value;

    @NonNull
    @SerializedName("line")
    private Integer line;
}
