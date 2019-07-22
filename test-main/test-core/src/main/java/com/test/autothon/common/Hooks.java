package com.test.autothon.common;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Rahul_Goyal
 */
public class Hooks {

    private final static Logger logger = LogManager.getLogger(Hooks.class);
    public static String scenarioName;

    @Before
    public void beforeExecution(Scenario scenario) {
        scenarioName = scenario.getName();
        scenarioName = scenarioName.replaceAll("\\s", "_");
        logger.info("Start Executing Scenario : [ " + scenarioName + " ]");
        logger.info("Deleting temp properties file");
        FileUtils.deleteFile(Constants.tempFileLocation);
    }

    @After
    public void afterExecution() {
        logger.info("Scenario Executions Ends");
        CustomHtmlReport.setHtmlReportPrefix();
        CustomHtmlReport.writeColumnValues();
        CustomHtmlReport.setHtmlReportSuffix();
        CustomHtmlReport.writetoHtmlReportFile();

    }

}
