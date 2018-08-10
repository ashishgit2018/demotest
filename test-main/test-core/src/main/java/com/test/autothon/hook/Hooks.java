package com.test.autothon.hook;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.log4j.Logger;

public class Hooks {

    private final static Logger logger = Logger.getLogger(Hooks.class);

    @Before
    public void beforeExecution(Scenario scenario) {
        logger.info("Start Executing Scenario : [ " + scenario.getName() + " ]");
    }

    @After
    public void afterExecution() {
        logger.info("Scenario Executions Ends");
    }

}
