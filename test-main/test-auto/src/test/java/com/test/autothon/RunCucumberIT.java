package com.test.autothon;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format="html:target/htmlReports.html",tags="@ui,@s1",  features="src/test/resources")
public class RunCucumberIT {
	//clean install verify -Dcucumber.options="--plugin json:target/results.json --tags @ui --tags @s1 src/test/resources"
}
