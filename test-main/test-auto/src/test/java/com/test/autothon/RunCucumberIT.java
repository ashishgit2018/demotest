package com.test.autothon;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/cucumber-reports/cucumber.json"}, tags = {"@testAuto", "@SN11"}, features = "src/test/resources")
public class RunCucumberIT {
    //clean install verify -Dcucumber.options="--plugin json:target/results.json --tags @ui --tags @s1 src/test/resources"
}
