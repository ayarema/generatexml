package net.testiteasy.generatexml.com.google.gson;

import net.testiteasy.generatexml.com.google.gson.rules.FieldAttributes;

public interface ExclusionStrategy {
    /**
     * @param f the field object that is under test
     * @return true if the field should be ignored; otherwise false
     */
    public boolean shouldSkipField(FieldAttributes f);

    /**
     * @param clazz the class object that is under test
     * @return true if the class should be ignored; otherwise false
     */
    public boolean shouldSkipClass(Class<?> clazz);
}
