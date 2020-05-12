package net.testiteasy.generatexml.utils.config;

import net.testiteasy.generatexml.GenerateXMLReportException;

import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    /**
     * This static initializer performs configuration data loading from class path
     */
    static {
        load("gxml.properties");
    }

    private static void load(final String name) {
        try {
            if (name != null) {
                properties.load(ConfigLoader.class.getClassLoader().getResourceAsStream(name));
            } else {
                throw new GenerateXMLReportException("Argument name should not be null nut is null");
            }
        } catch (Throwable e) {
            throw new GenerateXMLReportException("Unable to read " + name + " cause is " + e.getMessage());
        }
    }

    /**
     * Returns value, associated with configuration key
     *
     * @param key Configuration key
     * @return Associated value
     * @throws ArrayIndexOutOfBoundsException If value not configured
     */
    static String getValue(final String key) {
        if (key != null) {
            if (!properties.containsKey(key)) {
                throw new ArrayIndexOutOfBoundsException("Key " + key + " is not configured");
            }
            return properties.getProperty(key);
        } else {
            throw new GenerateXMLReportException("Argument key should not be null but is null");
        }
    }
}
