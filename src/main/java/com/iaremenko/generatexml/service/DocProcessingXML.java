package com.iaremenko.generatexml.service;

import com.google.gson.stream.JsonReader;
import com.iaremenko.generatexml.dto.ReportDocumentDto;
import com.iaremenko.generatexml.dto.elements.ElementDto;
import com.iaremenko.generatexml.dto.elements.steps.StepDto;
import com.iaremenko.generatexml.dto.elements.steps.docstrings.DocStringDto;
import com.iaremenko.generatexml.dto.elements.steps.matches.MatchDto;
import com.iaremenko.generatexml.dto.elements.steps.results.ResultDto;
import com.iaremenko.generatexml.dto.tags.TagDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocProcessingXML {

    private static final Logger LOGGER = LogManager.getLogger(DocProcessingXML.class);

    private ReportDocumentDto documentDto = new ReportDocumentDto();
    private List<TagDto> tagDtos = new ArrayList<>();
    private List<ElementDto> elementDtos = new ArrayList<>();
    private List<StepDto> stepDtos = new ArrayList<>();

    public ReportDocumentDto createObjectFromDoc(String substring) {
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

    private List<TagDto> deserializeTags(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeTags invoked");
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                TagDto tagDto = new TagDto();
                while (reader.hasNext()) {
                    String tagName = reader.nextName();
                    switch (tagName) {
                        case "name":
                            tagDto.setName(reader.nextString());
                            break;
                        case "line":
                            tagDto.setLine(reader.nextInt());
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                tagDtos.add(tagDto);
                reader.endObject();
            }
            reader.endArray();
            return tagDtos;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        } finally {
            LOGGER.debug("Method deserializeTags finally block invoked");
        }
    }

    private List<ElementDto> deserializeElements(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeElements invoked");
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                ElementDto elementDto = new ElementDto();
                while (reader.hasNext()) {
                    String elementName = reader.nextName();
                    switch (elementName) {
                        case "name":
                            elementDto.setName(reader.nextString());
                            break;
                        case "description":
                            elementDto.setDescription(reader.nextString());
                            break;
                        case "type":
                            elementDto.setType(reader.nextString());
                            break;
                        case "keyword":
                            elementDto.setKeyword(reader.nextString());
                            break;
                        case "id":
                            elementDto.setId(reader.nextString());
                            break;
                        case "steps":
                            elementDto.setSteps(deserializeSteps(reader));
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                elementDtos.add(elementDto);
                reader.endObject();
            }
            reader.endArray();
            return elementDtos;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        } finally {
            LOGGER.debug("Method deserializeElements finally block invoked");
        }
    }

    private List<StepDto> deserializeSteps(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeSteps invoked");
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                StepDto stepDto = new StepDto();
                while (reader.hasNext()) {
                    String stepName = reader.nextName();
                    switch (stepName) {
                        case "name":
                            stepDto.setName(reader.nextString());
                            break;
                        case "result":
                            stepDto.setResult(deserializeResults(reader));
                            break;
                        case "match":
                            stepDto.setMatch(deserializeMatch(reader));
                            break;
                        case "keyword":
                            stepDto.setKeyword(reader.nextString());
                            break;
                        case "line":
                            stepDto.setLine(reader.nextInt());
                            break;
                        case "doc_string":
                            stepDto.setDocString(deserializeDocString(reader));
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                stepDtos.add(stepDto);
                reader.endObject();
            }
            reader.endArray();
            return stepDtos;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return Collections.emptyList();
        } finally {
            LOGGER.debug("Method deserializeSteps finally block invoked");
        }
    }

    @Nullable
    private ResultDto deserializeResults(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeResults invoked");
            reader.beginObject();
            ResultDto resultDto = new ResultDto();
            while (reader.hasNext()) {
                String resultName = reader.nextName();
                switch (resultName) {
                    case "duration":
                        resultDto.setDuration(reader.nextLong());
                        break;
                    case "status":
                        resultDto.setStatus(reader.nextString());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            return resultDto;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            LOGGER.debug("Method deserializeResults block finally invoked");
        }
    }

    @Nullable
    private MatchDto deserializeMatch(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeMatch invoked");
            reader.beginObject();
            MatchDto matchDto = new MatchDto();
            while (reader.hasNext()) {
                String matchName = reader.nextName();
                switch (matchName) {
                    case "location":
                        matchDto.setLocation(reader.nextString());
                        break;
                    case "arguments":
                        reader.beginArray();
                        reader.endArray();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
                matchDto.setArguments(Collections.emptyList());
            }
            reader.endObject();
            return matchDto;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            LOGGER.debug("Method deserializeMatch finally block invoked");
        }
    }

    @Nullable
    private DocStringDto deserializeDocString(@NotNull JsonReader reader) {
        try {
            LOGGER.debug("Method deserializeDocString invoked");
            reader.beginObject();
            DocStringDto docStringDto = new DocStringDto();
            while (reader.hasNext()) {
                String docName = reader.nextName();
                switch (docName) {
                    case "content_type":
                        docStringDto.setContentType(reader.nextString());
                        break;
                    case "value":
                        docStringDto.setValue(reader.nextString().replace("\n", " "));
                        break;
                    case "line":
                        docStringDto.setLine(reader.nextInt());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            return docStringDto;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            LOGGER.debug("Method deserializeDocString finally block invoked");
        }
    }
}
