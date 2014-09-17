package org.connect4.utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class PropertiesUtil.
 */
public final class PropertiesUtil {

    /**
     * The Constructor.
     */
    private PropertiesUtil() {
        super();
    }

    /**
     * Load a properties file from the file system.
     * @param path
     *            the path
     * @return the properties
     */
    public static Properties loadFromFileSystem(final String path) {

        final Properties prop = new Properties();

        try (InputStream input = new FileInputStream(path)) {

            prop.load(input);

        } catch (final IOException e) {
            // do nothing
        }

        return prop;
    }

    /**
     * Load a properties file from project classpath.
     * @param filename
     *            the filename
     * @return the properties
     */
    public static Properties load(final String filename) {
        // For non-static method, use this :
        // prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

        final Properties prop = new Properties();

        try (final InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                return null;
            }
            prop.load(input);

        } catch (final IOException e) {
            // do nothing
        }
        return prop;
    }
}
