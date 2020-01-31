package com.easytestit.generatexml.parser;

import java.io.IOException;

/**
 * Copied from GSON library
 *
 * Thrown when a reader encounters malformed JSON. Some syntax errors can be
 * ignored by calling {@link JsonReader#setLenient(boolean)}.
 */
public class MalformedJsonException extends IOException {
    private static final long serialVersionUID = 1L;

    public MalformedJsonException(String msg) {
        super(msg);
    }

    public MalformedJsonException(String msg, Throwable throwable) {
        super(msg);
        // Using initCause() instead of calling super() because Java 1.5 didn't retrofit IOException
        // with a constructor with Throwable. This was done in Java 1.6
        initCause(throwable);
    }

    public MalformedJsonException(Throwable throwable) {
        // Using initCause() instead of calling super() because Java 1.5 didn't retrofit IOException
        // with a constructor with Throwable. This was done in Java 1.6
        initCause(throwable);
    }
}
