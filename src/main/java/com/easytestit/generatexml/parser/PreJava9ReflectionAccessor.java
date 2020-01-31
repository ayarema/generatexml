package com.easytestit.generatexml.parser;

import java.lang.reflect.AccessibleObject;

/**
 * Copied from GSON library
 *
 * A basic implementation of {@link ReflectionAccessor} which is suitable for Java 8 and below.
 * <p>
 * This implementation just calls {@link AccessibleObject#setAccessible(boolean) setAccessible(true)}, which worked
 * fine before Java 9.
 */
public final class PreJava9ReflectionAccessor extends ReflectionAccessor {
    /** {@inheritDoc} */
    @Override
    public void makeAccessible(AccessibleObject ao) {
        ao.setAccessible(true);
    }
}
