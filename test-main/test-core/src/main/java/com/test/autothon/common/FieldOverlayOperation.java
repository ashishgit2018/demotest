package com.test.autothon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Rahul_Goyal
 */
public class FieldOverlayOperation {

    private final static Logger logger = LogManager.getLogger(FieldOverlayOperation.class);

    private String fieldName;
    private String ordinalValue;

    public FieldOverlayOperation(String fieldName, String ordinalValue) {
        this.fieldName = fieldName;
        this.ordinalValue = ordinalValue;
    }

    public String overlayField() {
        String value = null;

        switch (fieldName.toUpperCase()) {
            case "PROPVALUE":
                value = ReadPropertiesFile.getPropertyValue(ordinalValue);
                break;
            case "RANDOMINTEGER":
                value = RandomGenerator.generateRandonIntegerValue(Integer.valueOf(ordinalValue));
                break;
            case "RANDOMSTRING":
                value = RandomGenerator.generateRandomStringValue(Integer.valueOf(ordinalValue));
                break;
            case "RANDOMALPHNUMER":
                value = RandomGenerator.generateRandomAlphaNumericValue(Integer.valueOf(ordinalValue));
                break;
        }
        return value;
    }

}
