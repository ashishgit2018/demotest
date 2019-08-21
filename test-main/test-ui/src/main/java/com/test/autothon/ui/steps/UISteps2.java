package com.test.autothon.ui.steps;

import com.test.autothon.common.ReadEnvironmentVariables;
import com.test.autothon.ui.core.AutothonTest;
import com.test.autothon.ui.core.UIOperations;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class UISteps2 extends AutothonTest {
		
	@And("^I launch \"([^\"]*)\"$")
    public void launchUrl(String url) throws Exception {
		launchUrl(url);

    }
		
    }
