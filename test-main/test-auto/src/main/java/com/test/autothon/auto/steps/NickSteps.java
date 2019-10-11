package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.NickWebsite;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.Given;

public class NickSteps extends StepDefinition {

    NickWebsite nickWebsite = new NickWebsite();

    @Given("Get details from Nick Website$")
    public void getNickDetails() {
        nickWebsite.setDeatilsFromNickWebSite();
    }

    @Given("^Check all the links are working$")
    public void checkLinkIsOk() {
        nickWebsite.checkLinksAreNotBroken();
    }

    @Given("^Check tooltip is not blank$")
    public void checkToolTip() {
        nickWebsite.checkToolTipValueIsNotBalnk();
    }

    @Given("^Validate all images are navigating$")
    public void checkAllImages() {
        nickWebsite.openUrlInNewTabAndCheckTitle();
    }


}