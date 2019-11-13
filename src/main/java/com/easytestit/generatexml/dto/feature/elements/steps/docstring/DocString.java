package com.easytestit.generatexml.dto.feature.elements.steps.docstring;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Getter;

@NoArgsConstructor
@NonNull
@Getter
public class DocString {

    @SerializedName("content_type")
    @Expose
    private String contentType;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("line")
    @Expose
    private Integer line;
}
