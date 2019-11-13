package com.easytestit.generatexml.dto.feature.elements.steps.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@NonNull
@Getter
public class Match {

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("arguments")
    @Expose
    private List<Object> arguments = null;

}
