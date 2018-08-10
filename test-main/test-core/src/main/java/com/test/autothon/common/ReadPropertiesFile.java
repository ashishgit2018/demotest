package com.test.autothon.common;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReadPropertiesFile {

    private final static Logger logger = Logger.getLogger(ReadPropertiesFile.class);
    private static Properties searchAllProps = new Properties();

    private static Properties loadPropertiesfile(final String propFileName) {
        FileInputStream input = null;
        try {
            input = new FileInputStream(propFileName);
            searchAllProps.load(input);
            input.close();
        } catch (FileNotFoundException e) {
            logger.error("Cannot open properties file " + propFileName + "\nException:\n " + e);
        } catch (IOException e) {
            logger.error("Cannot open properties file " + propFileName + "\nException:\n " + e);
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("Error closing input stream \nException:\n" + e);
                }
        }
        return searchAllProps;

    }

    public static void loadAllPropertiesValue() {
        List<String> results = new ArrayList<String>();
        File[] files = new File(Thread.currentThread().getContextClassLoader().getResource("config").getPath() + "/").listFiles();

        for (File file : files != null ? files : new File[0]) {
            if (file.isFile()) {
                results.add(file.getPath());
            }
        }

        int count = 0;
        while (count < results.size()) {
            loadPropertiesfile(results.get(count));
            count++;
        }
    }

    public static String getPropertyValue(String key) {
        String value = null;
        value = searchAllProps.getProperty(key);
        if (value == null) {
            loadAllPropertiesValue();
            value = searchAllProps.getProperty(key);
            if (value == null) logger.warn("Value for key <" + key + "> not found in properties file");
        }
        return value;
    }
}
