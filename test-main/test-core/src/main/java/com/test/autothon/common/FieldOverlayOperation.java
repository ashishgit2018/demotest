package com.test.autothon.common;

import org.apache.log4j.Logger;

public class FieldOverlayOperation {

    private final static Logger logger = Logger.getLogger(FieldOverlayOperation.class);

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
        }
        return value;
    }

}
