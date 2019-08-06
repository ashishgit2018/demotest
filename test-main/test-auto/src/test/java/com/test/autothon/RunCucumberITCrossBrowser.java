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
        tags = {"@crossbrowser"},
        features = "src/test/resources")
public class RunCucumberITCrossBrowser extends AbstractTestNGCucumberTests {

    @Parameters("browser")
    @BeforeTest()
    public void firstTestMethod(String browser) {
        ReadEnvironmentVariables.setBrowserName(browser);
    }
}
