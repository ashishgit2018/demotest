package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.Demo2019;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class DemoSteps {

    Demo2019 demo2019 = new Demo2019();

    @Given("The no. of movies")
    public void moviesCount(DataTable table) throws InterruptedException {
        List<String> movies = new ArrayList<>();
        movies =demo2019.createMovieList(Integer.parseInt(table.asList(String.class).get(0)));

        System.setProperty("webdriver.chrome.driver","C:\\ChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Thread.sleep(3000);

//        movies.add("The God Father");
        for(String movie_name: movies){
            driver.findElement(By.xpath(".//input[@name='q']")).sendKeys(movie_name+" wiki");
            driver.findElement(By.xpath(".//input[@name='q']")).sendKeys(Keys.RETURN);
//            driver.findElement(By.xpath("//*[@id='tsf']/div[2]/div/div[3]/center/input[1]")).click();
            driver.findElement(By.xpath(".//h3[contains(text(),'The Godfather - Wikipedia')]")).click();
        }

//        driver.findElement(By.id(".//input[@name='q']")).sendKeys("test"+" wiki");




        Thread.sleep(5000);
    }
}