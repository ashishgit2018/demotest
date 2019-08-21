package com.test.autothon.ui.core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AutothonTest {
	WebDriver driver ;
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
	
	public void launchUrl(String url) throws Exception {
      //Create driver object for Chrome
      System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
      driver = new ChromeDriver();
      driver.manage().window().maximize();


      //Navigate to a URL
      driver.get(url);
      driver.findElement(By.xpath("//input[@id=\"search\"]")).sendKeys("step-inforum");
      driver.findElement(By.xpath("//*[@id=\"search-icon-legacy\"]")).click();
      Thread.sleep(4000);
//      driver.switchTo().frame(1);
      driver.findElement(By.xpath("//h3[@id=\"channel-title\"]/span[contains(text(),\"STeP-IN Forum\")]")).click();
      Thread.sleep(3000);
      driver.findElement(By.xpath("//*[@id=\"tabsContent\"]/paper-tab[2]/div")).click();



//     harish

    /*  WebElement ele = driver.findElement(By.xpath("//*[@title=\"Run tests at scale with on-demand Selenium Grid using AWS Fargate\"]"));
      JavascriptExecutor js=(JavascriptExecutor) driver;
      Thread.sleep(5000);
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
      Thread.sleep(500);*/

//      WebElement element = driver.findElement(By.id("my-id"));
     /* Actions actions = new Actions(driver);
      actions.moveToElement(ele);
      actions.perform();*/



//      js.executeScript("arguments[0].scrollIntoView(true)", ele);
//   harish

      //*[@id="channel-title"]/span

//      fill the below line
//      driver.findElement(By.xpath("//h3[contains(@class ,'style-scope ytd-grid-video-renderer')]/a[@title=\"SUMMIT 2019 Promo\"]")).click();
//      int j =driver.findElements(By.xpath("//span[@class=\"style-scope ytd-compact-video-renderer\"]")).size();
//      System.out.println("the size is"+j);

//      List<WebElement> elementsxpath = driver.findElements(By.xpath("//span[@id=\"video-title\"]"));


//      List<WebElement> elementsxpath11 = driver.findElements(By.xpath("//*[@id=\"byline\"]"));

      Thread.sleep(8000);

      List<WebElement> elementsxpath = driver.findElements(By.xpath("//*[@id=\"video-title\"]"));
      List<String> upnextlist = new ArrayList<>();

      for (WebElement element : elementsxpath) {
          System.out.println("get title"+element.getText());
          System.out.println("the titles are"+element.getAttribute("title"));
          String temp =element.getAttribute("title").toString();
          upnextlist.add(temp);
//          System.out.println("get title"+element.getText());
//          System.out.println(element.);
      }

      for (String title:upnextlist){
//          System.out.println("video titles"+upnextlist);
      }
//      for(int i=0; i<elementsxpath.size(); i++) {
//          System.out.println(elementsxpath);
//         System.out.println(driver.findElement(By.xpath("elementsxpath")).getAttribute("title").toString());
//      }
      videoScroll(getRandomVideoTitle());
      JSONObject jObject = JsonWrite(getRandomVideoTitle(), upnextlist);
      post(jObject.toString());
}

		public void videoScroll(String videoTitle) throws InterruptedException {
			//WebElement ele = driver.findElement(By.xpath("//*[@title=\"Run tests at scale with on-demand Selenium Grid using AWS Fargate\"]"));
			WebElement ele = driver.findElement(By.xpath("//*[@title="+videoTitle+"]"));
		      JavascriptExecutor js=(JavascriptExecutor) driver;
		      Thread.sleep(5000);
		      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		}

}
