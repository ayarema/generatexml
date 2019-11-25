package com.easytestit.generatexml.dto.input.elements.steps.match;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@Getter
public class Match {

    @NonNull
    @SerializedName("location")
    private String location;

    @NonNull
    @SerializedName("arguments")
    private List<Object> arguments = null;

}
