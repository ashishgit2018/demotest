package com.test.autothon;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "junit:target/junit.xml", "json:target/cucumber-reports/cucumber.json"}, tags = {"@Nick"}, features = "src/test/resources")
public class RunCucumberIT {
}
