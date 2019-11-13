package com.easytestit.generatexml.dto.feature.tags;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@NonNull
@Getter
public class Tag {

    @SerializedName("name")
    private String name;

    @SerializedName("line")
    private Integer line;

}
