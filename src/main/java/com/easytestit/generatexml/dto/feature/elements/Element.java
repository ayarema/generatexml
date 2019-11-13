package com.easytestit.generatexml.dto.feature.elements;

import com.easytestit.generatexml.dto.feature.tags.Tag;
import com.google.gson.annotations.SerializedName;
import com.easytestit.generatexml.dto.feature.elements.steps.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@NonNull
@Getter
public class Element {

    @SerializedName("line")
    private Integer line;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("steps")
    private List<Step> steps;

    @SerializedName("tags")
    private List<Tag> tags;
}
