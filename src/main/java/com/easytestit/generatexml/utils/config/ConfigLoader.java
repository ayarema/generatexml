package com.easytestit.generatexml.utils.config;

import lombok.NonNull;

import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    /**
     * This static initializer performs configuration data loading from class path
     */
    static {
        System.out.println("Loading system properties");
        load("sender.properties");
    }

    private static void load(@NonNull final String name) {
        System.out.println("Reading " + name);
        try {
            properties.load(ConfigLoader.class.getClassLoader().getResourceAsStream(name));
            System.out.println("Reading " + name + " done successfully");
        } catch (Throwable e) {
            System.out.println("Unable to read " + name + " cause is " + e.getMessage());
        }
    }

    /**
     * Returns value, associated with configuration key
     *
     * @param key Configuration key
     * @return Associated value
     * @throws ArrayIndexOutOfBoundsException If value not configured
     */
    static String getValue(@NonNull final String key) {
        if (!properties.containsKey(key)) {
            throw new ArrayIndexOutOfBoundsException("Key " + key + " is not configured");
        }
        return properties.getProperty(key);
    }
}
