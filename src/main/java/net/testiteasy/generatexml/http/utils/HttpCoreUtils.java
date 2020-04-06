package net.testiteasy.generatexml.http.utils;

/**
 * Utility class {@link HttpCoreUtils} describes helper functions
 */
public class HttpCoreUtils {

    public static int positive(final int n, final String name) {
        if (n <= 0) {
            throw illegalArgumentException("%s must not be negative or zero: %d", name, n);
        }
        return n;
    }

    private static IllegalArgumentException illegalArgumentException(final String format, final Object... args) {
        return new IllegalArgumentException(String.format(format, args));
    }
}
