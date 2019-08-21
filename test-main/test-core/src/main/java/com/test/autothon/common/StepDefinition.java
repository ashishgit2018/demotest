package com.test.autothon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Rahul_Goyal
 */
public class StepDefinition {

    private final static Logger logger = LogManager.getLogger(StepDefinition.class);

    public static String getDateTimeStamp(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date());
    }

    public String getOverlay(String inputString) {
        if (inputString.isEmpty()) return "";
        inputString = inputString.replaceAll("(\\r|\\n)", "");
        if (inputString.matches(".*\\<.*\\(.*\\)\\>.*")) {
            return expandString(inputString);
        } else return inputString;
    }

    private String expandString(String inputString) {
        StringBuilder value = null;
        StringBuilder overlayElement = null;
        if (inputString.contains(" ")) {
            value = new StringBuilder();
            String[] arrElements = inputString.split("\\s");
            for (int i = 0; i < arrElements.length; i++) {
                overlayElement = new StringBuilder();
                if ((arrElements[i].contains("<") && arrElements[i].length() > 2) && !arrElements[i].contains(">")) {
                    overlayElement.append(arrElements[i]);
                    overlayElement.append(" ");
                    i++;
                    while (!arrElements[i].contains(">")) {
                        overlayElement.append(arrElements[i]);
                        overlayElement.append(" ");
                        i++;
                    }
                    overlayElement.append(arrElements[i]);
                    arrElements[i] = overlayElement.toString();
                }

                value.append(generateString(arrElements[i]));
                if (i < (arrElements.length - 1)) {
                    value.append(" ");
                }
            }
        } else {
            return generateString(inputString);
        }
        return value.toString();
    }

    private String generateString(String inputString) {
        if (!inputString.matches(".*\\<.*\\(.*\\)\\>.*") || inputString.matches(".*\\<{2,}.*\\(.*\\)\\>.*"))
            return inputString;

        String newInput;
        newInput = inputString.substring(0, inputString.indexOf("<"));
        String inputPart = inputString.substring(inputString.indexOf("<") + 1);
        String variableName = inputPart.substring(0, inputPart.indexOf(">"));
        logger.info("Processing: [ " + variableName + " ]");
        String value = getConfigValue(variableName);
        if (value != null) {
            newInput += value;
        } else {
            newInput += "";
        }
        newInput += inputPart.substring(variableName.length() + 1);
        logger.info("Processed Value : [ " + newInput + " ]");
        return newInput;
    }

    private String getConfigValue(String fieldVariable) {
        FieldOverlayOperation overlay = null;
        String searchValue = "";
        String ordinalValue = fieldVariable.substring(fieldVariable.indexOf("(") + 1, fieldVariable.indexOf(")", fieldVariable.indexOf("(")));
        String fieldName = fieldVariable.substring(0, fieldVariable.indexOf("("));
        overlay = new FieldOverlayOperation(fieldName, ordinalValue);
        searchValue = overlay.overlayField();
        return searchValue;
    }

    public void waitForSecond(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String executeCMDCommand(String cmd) {
        String result = null;
        logger.info("Running command " + cmd);

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream commandOutput = p.getInputStream();
            InputStream commandErr = p.getErrorStream();
            result = inputStreamToString(commandOutput) + inputStreamToString((commandErr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(result);
        return result;
    }

    private String inputStreamToString(InputStream stream) {
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        try {
            line = r.readLine();
            waitForSecond(2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

}
