package com.test.autothon.jmeter;

import com.test.autothon.common.Constants;
import com.test.autothon.common.FileUtils;
import com.test.autothon.common.ReadPropertiesFile;
import com.test.autothon.common.StepDefinition;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Rahul_Goyal
 */
public class JMeterManager extends StepDefinition {


    public final static String jmeterHomePath = ReadPropertiesFile.getPropertyValue("jmeter.home");
    public final static File jmeterHome = new File(jmeterHomePath);
    public final static String jmeterlogFilePath = "/target/jmeter_automation.jtl";
    public final static String jmeterHTMLFilePath = "/target/jmeter-reports/";
    public final static String jmeterJMXFilePath = jmeterHomePath + "/jmeter_automation.jmx";
    private final static Logger logger = LogManager.getLogger(JMeterManager.class);
    private StandardJMeterEngine jmeter;
    private String domain;
    private int port;
    private String httpMethod;
    private int noOfThreads;
    private int rampUp;
    private int noOfLoops;
    private HashTree testPlanTree;
    private File in;
    private boolean executionUsingJMX = false;


    public JMeterManager() {
        logger.info("Initializing the JMeter properties ...");
        if (!jmeterHome.exists()) {
            logger.error("jmeter.home property is not set..Exiting !!!");
            return;
        }
        String jmeterpropPath = jmeterHome.getPath() + "/bin/jmeter.properties";
        File jmeterProps = new File(jmeterpropPath);
        if (!jmeterProps.exists()) {
            logger.error(jmeterpropPath + " file not found...Exiting !!! ");
            return;
        }
        logger.info("JMeter Home :" + jmeterHome.getPath());
        logger.info("JMeter Properties :" + jmeterpropPath);

        jmeter = new StandardJMeterEngine();

        JMeterUtils.setJMeterHome(jmeterHome.getPath());
        JMeterUtils.loadJMeterProperties(jmeterProps.getPath());
        //JMeterUtils.initLogging(); // comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();
    }

    public void loadJMXFile(String jmeterJmxFile) {
        // Load existing .jmx Test Plan
        in = new File(Constants.jmeterResourcePath + "/" + jmeterJmxFile);
        executionUsingJMX = true;
    }

    public void setThreadProperties(int noOfThreads, int rampUp) {
        this.noOfThreads = noOfThreads;
        this.rampUp = rampUp;
    }

    public void setHttpSampler(String domain, int port, String httpMethod) {
        this.domain = domain;
        this.port = port;
        this.httpMethod = httpMethod.toUpperCase();
    }

    public void setLoopControler(int noOfLoops) {
        this.noOfLoops = noOfLoops;
    }

    private ThreadGroup getThreadGroup() {
        logger.info("Creating ThreadGroup object with - " +
                "No Of Threads :" + noOfThreads + " \t Ramp Up :" + rampUp);
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Jmeter Automation Thread");
        threadGroup.setNumThreads(noOfThreads);
        threadGroup.setRampUp(rampUp);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        return threadGroup;
    }

    private HTTPSamplerProxy getHttpSampler() {
        logger.info("Creating HTTPSampler Object with - " +
                "Domain :" + domain + " \t Port :" + port + " \t HTTP method :" + httpMethod);
        HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
        if (domain.startsWith("http")) {
            if (domain.split("//")[0].contains("https"))
                httpSampler.setProtocol("https");
            else
                httpSampler.setProtocol("http");
            domain = domain.split("//")[1];
        }
        httpSampler.setDomain(domain);
        httpSampler.setPort(port);
        httpSampler.setPath("/");
        httpSampler.setMethod(httpMethod);
        httpSampler.setFollowRedirects(true);
        httpSampler.setUseKeepAlive(true);
        httpSampler.setName("Http Sampler for : " + httpMethod + "-" + domain + ":" + port);
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        return httpSampler;
    }

    private LoopController getLoopController() {
        logger.info("Creating LoopController Object with - " +
                "No Of Loops :" + noOfLoops);
        LoopController loopController = new LoopController();
        loopController.setLoops(noOfLoops);
        //loopController.addTestElement(getHttpSampler());
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();
        return loopController;
    }

    private HashTree generateTestPlan() {

        logger.info("Creating Jmeter Test Plan");
        testPlanTree = new HashTree();

        LoopController loopController = getLoopController();
        ThreadGroup threadGroup = getThreadGroup();
        threadGroup.setSamplerController(loopController);

        TestPlan testPlan = new TestPlan("JMeter Automation Test Plan");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        testPlanTree.add(testPlan);
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        threadGroupHashTree.add(getHttpSampler());
        return testPlanTree;
    }

    private void saveTestPlan() {
        logger.info("Saving the Jmeter test plan");
        try {
            SaveService.saveTree(testPlanTree, new FileOutputStream(jmeterJMXFilePath));
            logger.info("Jmeter JMX file location :" + jmeterJMXFilePath);
            Summariser summariser = null;
            String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
            if (summariserName.length() > 0) {
                summariser = new Summariser(summariserName);
            }
            logger.info("Saving the JMeter output log file");
            logger.info("JMeter log file location :" + jmeterlogFilePath);
            ResultCollector rlogger = new ResultCollector(summariser);
            rlogger.clearData();
            rlogger.setFilename("." + jmeterlogFilePath);
            testPlanTree.add(testPlanTree.getArray()[0], rlogger);

        } catch (IOException e) {
            logger.error("Error saving the Jmeter test plan \n" + e);
        }
    }

    private void generateHTMLReports() {
        //delete existing report folder
        FileUtils.deleteFolder("." + jmeterHTMLFilePath);
        String cmd = "jmeter -g " + System.getProperty("user.dir") + jmeterlogFilePath + " -o " + System.getProperty("user.dir") + jmeterHTMLFilePath;
        executeCMDCommand(cmd);
    }

    public void runJmeterTests() {
        logger.info("Executing the JMeter Tests ...");
        if (jmeter == null) {
            return;
        }
        if (executionUsingJMX) {
            if (in != null && in.exists()) {
                try {
                    testPlanTree = SaveService.loadTree(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("Loading input JMX file " + in.getPath());
            } else {
                logger.error("The input JMX file does not exists...exiting..");
                return;
            }
        } else
            generateTestPlan();
        saveTestPlan();
        jmeter.configure(testPlanTree);
        jmeter.run();
        generateHTMLReports();
        logger.info("JMeter Test Execution Completed !!!");
    }

    public void integrateJMeterWithSelenium(String url) {
        int noOfThread = Integer.valueOf(ReadPropertiesFile.getPropertyValue("jmeter.default.thread"));
        int ramp = Integer.valueOf(ReadPropertiesFile.getPropertyValue("jmeter.default.ramp"));
        int loop = Integer.valueOf(ReadPropertiesFile.getPropertyValue("jmeter.default.loop"));

        setThreadProperties(noOfThread, ramp);
        setLoopControler(loop);
        setHttpSampler(url, -1, "GET");
        runJmeterTests();
    }

}