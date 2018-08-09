package com.test.autothon;

public class FieldOverlayOperation {
	
	private String fieldName;
	private String ordinalValue;

	public FieldOverlayOperation(String fieldName, String ordinalValue) {
		this.fieldName = fieldName;
		this.ordinalValue = ordinalValue;
	}

	public String overlayField() {
		String value = null;
		
		switch (fieldName.toUpperCase()){
		case "PROPVALUE":
			value = ReadPropertiesFile.getPropertyValue(ordinalValue);
		}
		return value;
	}

}
