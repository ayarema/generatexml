package com.easytestit.generatexml.dto.input;

import com.easytestit.generatexml.dto.input.elements.Element;
import com.easytestit.generatexml.dto.input.tags.Tag;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * The root class which described logic of storing data which stored in JSON file and need it to XML file
 * Moreover, it's a class which is a start point for test JSON report data
 */
@NoArgsConstructor
@Getter
public class Feature {

    @NonNull
    @SerializedName("line")
    private Integer line;

    @NonNull
    @SerializedName("elements")
    private List<Element> elements;

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
    @SerializedName("keyword")
    private String keyword;

    @NonNull
    @SerializedName("uri")
    private String uri;

    @NonNull
    @SerializedName("tags")
    private List<Tag> tags;

}
