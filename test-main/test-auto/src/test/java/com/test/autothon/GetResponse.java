package com.test.autothon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GetResponse {

	public static void main(String[] args) throws Exception {
		String response = getRandomVideoTitle();
		System.out.println(response);
		List<String> videoList = new ArrayList<String>();
		videoList.add("sample 1");
		videoList.add("sample 2");
		videoList.add("sample 3");
		JSONObject jObject = JsonWrite(response, videoList);
		//uploadToServer(jObject);
		//runningCurl();
		post(jObject.toString());
	}
	
	public static String getRandomVideoTitle() throws IOException{
		System.out.println("get Random Video Title");
		URL url = new URL("http://54.169.34.162:5252/video");
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();

		int code = connection.getResponseCode();
		
		InputStream in = connection.getInputStream();
		String encoding = connection.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String body = IOUtils.toString(in, encoding);
		//body = body.split("|")[0];
		System.out.println(body);
		body.replaceAll("\u2013", "");
		return body;
	}
	
	public static JSONObject JsonWrite(String videoName, List<String> videoList) {

		JSONObject obj = new JSONObject();
		obj.put("team", "CME Group - 2");
		obj.put("video", videoName);
		JSONArray list = new JSONArray();
		for(String vList: videoList) {
			list.add(vList);
		}

		obj.put("upcoming-videos", list);//adding the list to our JSON Object

		try {
			FileWriter file = new FileWriter("c:\\automation\\videoList.json");
			file.write(obj.toJSONString());
			file.flush();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(obj);
		return obj;
	}
	
	public static void post(String param ) throws Exception{
		  String charset = "UTF-8"; 
		  URLConnection connection = new URL("http://54.169.34.162:5252/upload").openConnection();
		  connection.setDoOutput(true);
		  //connection.setRequestProperty("Accept-Charset", charset);
		  //connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
		  connection.setRequestProperty("Content-Type", "application/json");

		  try (OutputStream output = connection.getOutputStream()) {
		    output.write(param.getBytes());
		  }

		  InputStream response = connection.getInputStream();
		  System.out.println(response);
		}
}
