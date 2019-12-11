package com.easytestit.generatexml.dto.input.tags;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Getter
public class Tag {

    @NonNull
    @SerializedName("name")
    private String name;

    @NonNull
    @SerializedName("line")
    private Integer line;

}
