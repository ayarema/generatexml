package net.testiteasy.generatexml.com.google.gson;

import net.testiteasy.generatexml.com.google.gson.internal.JavaVersion;

import java.lang.reflect.AccessibleObject;

/**
 * Copied from GSON library
 *
 * Provides a replacement for {@link AccessibleObject#setAccessible(boolean)}, which may be used to
 * avoid reflective access issues appeared in Java 9, like {@link java.lang.reflect.InaccessibleObjectException}
 * thrown or warnings like
 * <pre>
 *   WARNING: An illegal reflective access operation has occurred
 *   WARNING: Illegal reflective access by ...
 * </pre>
 * <p/>
 * Works both for Java 9 and earlier Java versions.
 */
public abstract class ReflectionAccessor {
    // the singleton instance, use getInstance() to obtain
    private static final ReflectionAccessor instance = JavaVersion.getMajorJavaVersion() < 9 ? new PreJava9ReflectionAccessor() : new UnsafeReflectionAccessor();

    /**
     * Does the same as {@code ao.setAccessible(true)}, but never throws
     * {@link java.lang.reflect.InaccessibleObjectException}
     */
    public abstract void makeAccessible(AccessibleObject ao);

    /**
     * Obtains a {@link ReflectionAccessor} instance suitable for the current Java version.
     * <p>
     * You may need one a reflective operation in your code throws {@link java.lang.reflect.InaccessibleObjectException}.
     * In such a case, use {@link ReflectionAccessor#makeAccessible(AccessibleObject)} on a field, method or constructor
     * (instead of basic {@link AccessibleObject#setAccessible(boolean)}).
     */
    public static ReflectionAccessor getInstance() {
        return instance;
    }
}
