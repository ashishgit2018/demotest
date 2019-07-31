package com.test.autothon;

import com.test.autothon.common.ReadEnvironmentVariables;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "junit:target/junit.xml", "json:target/cucumber-reports/cucumber.json"},
        tags = {"@SN11"},
        features = "src/test/resources")
public class RunCucumberITCrossBrowser extends AbstractTestNGCucumberTests {

    @Parameters("browser")
    @BeforeTest()
    public void firstTestMethod(String browser) {
        System.out.println("Browser: " + browser);
        ReadEnvironmentVariables.setBrowserName(browser);
    }
}
