package com.test.autothon.step;

import com.test.autothon.common.StepDefinition;
import com.test.autothon.jmeter.JMeterManager;
import cucumber.api.java.en.Given;

/**
 * @author Rahul_Goyal
 */
public class JMeterSteps extends StepDefinition {

    private JMeterManager jm;

    public JMeterSteps() {
        jm = new JMeterManager();

    }

    @Given("^Set JMeter No of threads as \"(.*?)\", No of loops as \"(.*?)\" and Ramp up as \"(.*?)\"$")
    public void setJmeterThreadProps(String noOfThreads, String noOfLoops, String rampUp) {
        int threads = Integer.valueOf(getOverlay(noOfThreads));
        int loops = Integer.valueOf(getOverlay(noOfLoops));
        int ramps = Integer.valueOf(getOverlay(rampUp));
        jm.setThreadProperties(threads, ramps);
        jm.setLoopControler(loops);
    }

    @Given("^Set JMeter test domain as \"(.*?)\", port as \"(.*?)\" and Http method as \"(.*?)\"$")
    public void setJmeterHttpParameters(String domain, String portNo, String httpMethod) {
        domain = getOverlay(domain);
        int port = Integer.valueOf(getOverlay(portNo));
        httpMethod = getOverlay(httpMethod);
        jm.setHttpSampler(domain, port, httpMethod);
    }

    @Given("^Load the JMeter JMX file with name as \"(.*?)\"$")
    public void loadJmeterJMXFile(String fileName) {
        fileName = getOverlay(fileName);
        jm.loadJMXFile(fileName);
    }

    @Given("^Execute the JMeter test$")
    public void runJmeterTests() {
        jm.runJmeterTests();
    }
}