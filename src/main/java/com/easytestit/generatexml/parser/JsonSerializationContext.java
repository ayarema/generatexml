package com.easytestit.generatexml.parser;

import java.lang.reflect.Type;

public interface JsonSerializationContext {
    /**
     * Invokes default serialization on the specified object.
     *
     * @param src the object that needs to be serialized.
     * @return a tree of {@link JsonElement}s corresponding to the serialized form of {@code src}.
     */
    public JsonElement serialize(Object src);

    /**
     * Invokes default serialization on the specified object passing the specific type information.
     * It should never be invoked on the element received as a parameter of the
     * {@link JsonSerializer#serialize(Object, Type, JsonSerializationContext)} method. Doing
     * so will result in an infinite loop since Gson will in-turn call the custom serializer again.
     *
     * @param src the object that needs to be serialized.
     * @param typeOfSrc the actual genericized type of src object.
     * @return a tree of {@link JsonElement}s corresponding to the serialized form of {@code src}.
     */
    public JsonElement serialize(Object src, Type typeOfSrc);
}
