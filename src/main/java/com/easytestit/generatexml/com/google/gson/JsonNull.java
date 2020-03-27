package com.easytestit.generatexml.com.google.gson;

public final class JsonNull extends JsonElement {
    /**
     * singleton for JsonNull
     *
     * @since 1.8
     */
    public static final JsonNull INSTANCE = new JsonNull();

    /**
     * Creates a new JsonNull object.
     * Deprecated since Gson version 1.8. Use {@link #INSTANCE} instead
     */
    @Deprecated
    public JsonNull() {
        // Do nothing
    }

    /**
     * Returns the same instance since it is an immutable value
     * @since 2.8.2
     */
    @Override
    public JsonNull deepCopy() {
        return INSTANCE;
    }

    /**
     * All instances of JsonNull have the same hash code since they are indistinguishable
     */
    @Override
    public int hashCode() {
        return JsonNull.class.hashCode();
    }

    /**
     * All instances of JsonNull are the same
     */
    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof JsonNull;
    }
}
