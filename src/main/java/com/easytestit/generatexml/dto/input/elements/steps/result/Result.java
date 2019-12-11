package com.easytestit.generatexml.dto.input.elements.steps.result;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Getter
public class Result {

    @NonNull
    @SerializedName("duration")
    private Long duration;

    @NonNull
    @SerializedName("error_message")
    private String errorMessage;

    @NonNull
    @SerializedName("status")
    private String status;

}
