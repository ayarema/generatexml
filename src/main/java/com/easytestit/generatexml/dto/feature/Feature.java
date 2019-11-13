package com.easytestit.generatexml.dto.feature;

import com.easytestit.generatexml.dto.feature.elements.Element;
import com.easytestit.generatexml.dto.feature.tags.Tag;
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
@NonNull
@Getter
public class Feature {

    @SerializedName("line")
    private Integer line;

    @SerializedName("elements")
    private List<Element> elements = null;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("keyword")
    private String keyword;

    @SerializedName("uri")
    private String uri;

    @SerializedName("tags")
    private List<Tag> tags;

}
