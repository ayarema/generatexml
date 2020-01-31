package com.easytestit.generatexml.parser;

public enum LongSerializationPolicy {
    /**
     * This is the "default" serialization policy that will output a {@code long} object as a JSON
     * number. For example, assume an object has a long field named "f" then the serialized output
     * would be:
     * {@code {"f":123}}.
     */
    DEFAULT() {
        @Override public JsonElement serialize(Long value) {
            return new JsonPrimitive(value);
        }
    },

    /**
     * Serializes a long value as a quoted string. For example, assume an object has a long field
     * named "f" then the serialized output would be:
     * {@code {"f":"123"}}.
     */
    STRING() {
        @Override public JsonElement serialize(Long value) {
            return new JsonPrimitive(String.valueOf(value));
        }
    };

    /**
     * Serialize this {@code value} using this serialization policy.
     *
     * @param value the long value to be serialized into a {@link JsonElement}
     * @return the serialized version of {@code value}
     */
    public abstract JsonElement serialize(Long value);
}
