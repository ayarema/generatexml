package net.testiteasy.generatexml.com.google.gson.stream;

import java.io.IOException;

/**
 * Thrown when a reader encounters malformed JSON. Some syntax errors can be
 * ignored by calling {@link JsonReader#setLenient(boolean)}.
 */
public class MalformedJsonException extends IOException {
    private static final long serialVersionUID = 1L;

    public MalformedJsonException(String msg) {
        super(msg);
    }
}
