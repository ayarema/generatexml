package com.easytestit.generatexml.dto.input.elements;

import com.easytestit.generatexml.dto.input.tags.Tag;
import com.google.gson.annotations.SerializedName;
import com.easytestit.generatexml.dto.input.elements.steps.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@Getter
public class Element {

    @NonNull
    @SerializedName("line")
    private Integer line;

    @NonNull
    @SerializedName("name")
    private String name;

    @NonNull
    @SerializedName("description")
    private String description;

    @NonNull
    @SerializedName("id")
    private String id;

    @NonNull
    @SerializedName("type")
    private String type;

    @NonNull
    @SerializedName("keyword")
    private String keyword;

    @NonNull
    @SerializedName("steps")
    private List<Step> steps;

    @NonNull
    @SerializedName("tags")
    private List<Tag> tags;

}
