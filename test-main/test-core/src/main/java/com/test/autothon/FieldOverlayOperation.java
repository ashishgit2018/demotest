package com.test.autothon;

public class FieldOverlayOperation {
	
	private String fieldName;
	private String ordinalValue;

	public FieldOverlayOperation(String fieldName, String ordinalValue) {
		fieldName = this.fieldName;
		ordinalValue = this.ordinalValue;
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
