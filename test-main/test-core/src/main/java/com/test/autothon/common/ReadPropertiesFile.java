package com.test.autothon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Rahul_Goyal
 */
public class ReadPropertiesFile {

    private final static Logger logger = LogManager.getLogger(ReadPropertiesFile.class);
    private static Properties searchAllProps = new Properties();

    public static Properties loadPropertiesfile(final String propFileName) {
        FileInputStream input = null;
        Properties prop = new Properties();
        try {
            input = new FileInputStream(propFileName);
            prop.load(input);
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
        return prop;

    }

    public static void loadAllPropertiesValue() {
        List<String> results = new ArrayList<String>();
        logger.debug("Reading properties file from location: " + Constants.configResourcePath + "/");
        File[] files = new File(Constants.configResourcePath + "/").listFiles();

        for (File file : files != null ? files : new File[0]) {
            if (file.isFile()) {
                results.add(file.getPath());
            }
        }

        logger.debug("No of Properties file loaded is: " + results.size());

        int count = 0;
        while (count < results.size()) {
            searchAllProps.putAll(loadPropertiesfile(results.get(count)));
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

    public static void writeToProperties(String key, String value, String filePath) {
        FileOutputStream out = null;
        File file = new File(filePath);
        try {
            if (!file.exists())
                file.createNewFile();
            Properties prop = loadPropertiesfile(filePath);
            out = new FileOutputStream(filePath);
            prop.setProperty(key, value);
            searchAllProps.setProperty(key, value);
            prop.store(out, null);
            out.close();
        } catch (FileNotFoundException e) {
            logger.error(filePath + " not found\n" + e);
        } catch (IOException e) {
            logger.error("Error closing file\n" + e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("Error closing file\n" + e);
            }
        }
        logger.info("Setting key <" + key + "> \t value <" + value + ">");
    }
}
