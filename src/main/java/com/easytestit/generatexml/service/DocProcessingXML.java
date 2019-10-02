package com.easytestit.generatexml.service;

import com.google.gson.stream.JsonReader;
import com.easytestit.generatexml.dto.feature.Feature;
import com.easytestit.generatexml.dto.feature.elements.Element;
import com.easytestit.generatexml.dto.feature.elements.steps.Step;
import com.easytestit.generatexml.dto.feature.elements.steps.docstrings.DocString;
import com.easytestit.generatexml.dto.feature.elements.steps.matches.Match;
import com.easytestit.generatexml.dto.feature.elements.steps.results.Result;
import com.easytestit.generatexml.dto.feature.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @deprecated functionality
 */
@Deprecated
public class DocProcessingXML {

    private static final Logger LOGGER = LogManager.getLogger(DocProcessingXML.class);

    private Feature documentDto = new Feature();
    private List<Tag> tags = new ArrayList<>();
    private List<Element> elements = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();

    public DocProcessingXML() {

    }

    public Feature deserializeReportDocumentToObject(String substring) {
        try {
            LOGGER.info("Method createObjectFromDoc invoked");
            JsonReader reader = new JsonReader(new StringReader(substring));
            reader.beginObject();
            while (reader.hasNext()) {
                String root = reader.nextName();
                switch (root) {
                    case "name":
                        documentDto.setName(reader.nextString());
                        break;
                    case "description":
                        documentDto.setDescription(reader.nextString().replace("\n", " "));
                        break;
                    case "id":
                        documentDto.setId(reader.nextString());
                        break;
                    case "keyword":
                        documentDto.setKeyword(reader.nextString());
                        break;
                    case "uri":
                        documentDto.setUri(reader.nextString());
                        break;
                    case "tags":
                        documentDto.setTags(deserializeTags(reader));
                        break;
                    case "elements":
                        documentDto.setElements(deserializeElements(reader));
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            reader.close();
            return documentDto;
        } catch (IOException ie) {
            LOGGER.error(ie.getMessage());
            return null;
        } finally {
            LOGGER.debug("Method createObjectFromDoc finally block invoked");
        }
    }

    private List<Tag> deserializeTags(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeTags invoked");
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                Tag tag = new Tag();
                while (reader.hasNext()) {
                    String tagName = reader.nextName();
                    switch (tagName) {
                        case "name":
                            tag.setName(reader.nextString());
                            break;
                        case "line":
                            tag.setLine(reader.nextInt());
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                tags.add(tag);
                reader.endObject();
            }
            reader.endArray();
            return tags;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        } finally {
            LOGGER.debug("Method deserializeTags finally block invoked");
        }
    }

    private List<Element> deserializeElements(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeElements invoked");
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                Element element = new Element();
                while (reader.hasNext()) {
                    String elementName = reader.nextName();
                    switch (elementName) {
                        case "name":
                            element.setName(reader.nextString());
                            break;
                        case "description":
                            element.setDescription(reader.nextString());
                            break;
                        case "type":
                            element.setType(reader.nextString());
                            break;
                        case "keyword":
                            element.setKeyword(reader.nextString());
                            break;
                        case "id":
                            element.setId(reader.nextString());
                            break;
                        case "steps":
                            element.setSteps(deserializeSteps(reader));
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                elements.add(element);
                reader.endObject();
            }
            reader.endArray();
            return elements;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        } finally {
            LOGGER.debug("Method deserializeElements finally block invoked");
        }
    }

    private List<Step> deserializeSteps(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeSteps invoked");
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                Step step = new Step();
                while (reader.hasNext()) {
                    String stepName = reader.nextName();
                    switch (stepName) {
                        case "name":
                            step.setName(reader.nextString());
                            break;
                        case "result":
                            step.setResult(deserializeResults(reader));
                            break;
                        case "match":
                            step.setMatch(deserializeMatch(reader));
                            break;
                        case "keyword":
                            step.setKeyword(reader.nextString());
                            break;
                        case "line":
                            step.setLine(reader.nextInt());
                            break;
                        case "doc_string":
                            step.setDocString(deserializeDocString(reader));
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                steps.add(step);
                reader.endObject();
            }
            reader.endArray();
            return steps;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        } finally {
            LOGGER.debug("Method deserializeSteps finally block invoked");
        }
    }

    @Nullable
    private Result deserializeResults(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeResults invoked");
            reader.beginObject();
            Result result = new Result();
            while (reader.hasNext()) {
                String resultName = reader.nextName();
                switch (resultName) {
                    case "duration":
                        result.setDuration(reader.nextLong());
                        break;
                    case "status":
                        result.setStatus(reader.nextString());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            return result;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            LOGGER.debug("Method deserializeResults block finally invoked");
        }
    }

    @Nullable
    private Match deserializeMatch(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeMatch invoked");
            reader.beginObject();
            Match match = new Match();
            while (reader.hasNext()) {
                String matchName = reader.nextName();
                switch (matchName) {
                    case "location":
                        match.setLocation(reader.nextString());
                        break;
                    case "arguments":
                        reader.beginArray();
                        reader.endArray();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
                match.setArguments(Collections.emptyList());
            }
            reader.endObject();
            return match;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            LOGGER.debug("Method deserializeMatch finally block invoked");
        }
    }

    @Nullable
    private DocString deserializeDocString(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeDocString invoked");
            reader.beginObject();
            DocString docString = new DocString();
            while (reader.hasNext()) {
                String docName = reader.nextName();
                switch (docName) {
                    case "content_type":
                        docString.setContentType(reader.nextString());
                        break;
                    case "value":
                        docString.setValue(reader.nextString().replace("\n", " "));
                        break;
                    case "line":
                        docString.setLine(reader.nextInt());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            return docString;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            LOGGER.debug("Method deserializeDocString finally block invoked");
        }
    }
}
