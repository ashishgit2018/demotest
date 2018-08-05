package com.test.autothon.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cucumber.api.java.After;

public class AutomationUIUtils {
	static String tagName;
	static String scrFilePath;
	static String testCaseName;
	static List<String> base64Images=new ArrayList<String>();
	
	public static String getTestCaseName() {
		return testCaseName;
	}
	
	public static void setTestCaseName(String tcName) {
		testCaseName=tcName;
	}
	
	public static String getTagName() {
		return tagName;
	}
	
	public static void setTagName(String tag) {
		tagName=tag;
	}
	
	public static void setSrcFilePath(String filePath) {
		scrFilePath=filePath;
	}
	
	public static String getSrcFilePath() {
		return scrFilePath;
	}
	
	public static List<String> getBase64Images() {
		return base64Images;
	}
	
	public static void setBase64Image(String base64Image) {
		base64Images.add(base64Image);
	}
	
	public static void setBase64ImageToNull() {
		base64Images.clear();
	}
	
	public static String getDateTimeStamp() {
		SimpleDateFormat format=new SimpleDateFormat("yyyymmddhhmmss");
		return format.format(new Date());
	}
}
