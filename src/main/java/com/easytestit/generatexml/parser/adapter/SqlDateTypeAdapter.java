package com.easytestit.generatexml.parser.adapter;

import com.easytestit.generatexml.parser.JsonParser;
import com.easytestit.generatexml.parser.JsonSyntaxException;
import com.easytestit.generatexml.parser.reflect.TypeToken;
import com.easytestit.generatexml.parser.stream.JsonReader;
import com.easytestit.generatexml.parser.stream.JsonToken;
import com.easytestit.generatexml.parser.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Copied from GSON library
 *
 * Adapter for java.sql.Date. Although this class appears stateless, it is not.
 * DateFormat captures its time zone and locale when it is created, which gives
 * this class state. DateFormat isn't thread safe either, so this class has
 * to synchronize its read and write methods.
 */
public final class SqlDateTypeAdapter extends TypeAdapter<java.sql.Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
        @Override public <T> TypeAdapter<T> create(JsonParser jsonParser, TypeToken<T> typeToken) {
            return typeToken.getRawType() == java.sql.Date.class
                    ? (TypeAdapter<T>) new SqlDateTypeAdapter() : null;
        }
    };

    private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");

    @Override
    public synchronized java.sql.Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            final long utilDate = format.parse(in.nextString()).getTime();
            return new java.sql.Date(utilDate);
        } catch (ParseException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public synchronized void write(JsonWriter out, java.sql.Date value) throws IOException {
        out.value(value == null ? null : format.format(value));
    }
}
