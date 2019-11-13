package com.easytestit.generatexml.dto.feature.elements.steps.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@NonNull
@Getter
public class Result {

    @SerializedName("duration")
    @Expose
    private Long duration;

    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    @SerializedName("status")
    @Expose
    private String status;

}
