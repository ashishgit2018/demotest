package com.test.autothon.common;

import org.apache.log4j.Logger;

import java.io.*;

public class FileUtils {

    private final static Logger logger = Logger.getLogger(FileUtils.class);

    private FileUtils() {

    }

    public static String readFileAsString(String filePath, String fileName) {
        String fileFullPath = filePath + File.separator + fileName;
        return readFileAsString(fileFullPath);
    }

    public static String readFileAsString(String fileFullPath) {
        logger.info("Reading file : " + fileFullPath);
        byte[] buffer = new byte[(int) new File(fileFullPath).length()];
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(fileFullPath));
            if (inputStream.read(buffer) > 0)
                return new String(buffer);
        } catch (FileNotFoundException e) {
            logger.error("File : " + fileFullPath + " not found \n" + e);
        } catch (IOException e) {
            logger.error("Error reading file : " + fileFullPath + " \n" + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Error closing file : " + fileFullPath + " \n" + e);
                }
            }
        }
        return "";
    }

    public static void writeToTempFile(String key, String value) {
        ReadPropertiesFile.writeToProperties(key, value, Constants.tempFileLocation);
    }

    public static void deleteFile(String fileName) {
        logger.info("Deleting file : " + fileName);
        File file = new File(fileName);
        if (file.exists())
            file.delete();
    }

}
