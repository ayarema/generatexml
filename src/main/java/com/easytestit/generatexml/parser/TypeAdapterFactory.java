package com.easytestit.generatexml.parser;

public interface TypeAdapterFactory {
    /**
     * Returns a type adapter for {@code type}, or null if this factory doesn't
     * support {@code type}.
     */
    <T> TypeAdapter<T> create(Json json, TypeToken<T> type);
}
