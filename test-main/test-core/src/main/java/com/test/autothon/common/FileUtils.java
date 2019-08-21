package com.test.autothon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * @author Rahul_Goyal
 */
public class FileUtils {

    private final static Logger logger = LogManager.getLogger(FileUtils.class);

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

    public static void writeToFile(String fileName, String content) {
        logger.info("Writing to file: " + fileName);
        logger.info("Writing value: " + content);
        FileOutputStream out = null;
        File file = new File(fileName);
        try {
            if (!file.exists())
                file.createNewFile();
            out = new FileOutputStream(fileName);
            out.write(content.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void deleteFile(String fileName) {
        logger.info("Deleting file : " + fileName);
        File file = new File(fileName);
        if (file.exists())
            file.delete();
    }

    public static void createFolder(String folder) {
        logger.info("Creating Folder : " + folder);
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFolder(String folder) {
        logger.info("Deleting Folder : " + folder);
        File file = new File(folder);
        deleteAllFiles(file);
        file.delete();
    }

    public static void deleteAllFiles(File dir) {

        File[] toBeDeleted;

        try {
            // returns pathnames for files and directory
            toBeDeleted = dir.listFiles();
            // for each pathname in pathname array
            if (toBeDeleted != null) {
                for (File f : toBeDeleted) {
                    if (f.exists() && !f.isDirectory()) {
                        if (!f.delete()) {
                            logger.info("Can't remove " + f.getAbsolutePath());
                        }
                    } else if (f.exists() && f.isDirectory()) {
                        deleteAllFiles(f);
                        f.delete();
                    }
                }

            }
        } catch (Exception e) {
            // if any error occurs
            logger.error("Failed to delete file." + e);
        }

    }

    public static File getResourceAsFile(Object obj, String resourcePath, String fileExtension) {
        try {
            InputStream in = obj.getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                logger.error("Could not read or find the " + resourcePath + " file");
                return null;
            }
            if (!fileExtension.startsWith("."))
                fileExtension = "." + fileExtension;
            File tempfile = null;
            tempfile = File.createTempFile(String.valueOf(in.hashCode()), fileExtension);
            tempfile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempfile)) {
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.close();
                in.close();
                return tempfile;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
