package com.easytestit.generatexml.parser.rules;

import java.lang.reflect.Field;

public interface FieldNamingStrategy {
    /**
     * Translates the field name into its JSON field name representation.
     *
     * @param f the field object that we are translating
     * @return the translated field name.
     * @since 1.3
     */
    public String translateName(Field f);
}
