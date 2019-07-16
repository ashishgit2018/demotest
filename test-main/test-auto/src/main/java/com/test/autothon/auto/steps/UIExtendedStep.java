package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.UIExtension;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import java.io.IOException;

public class UIExtendedStep extends StepDefinition {

    UIExtension uiExtension = new UIExtension();

    @And("^I select \"([^\"]*)\"$")
    public void BuyItem(String itemName) throws Exception {
        itemName = getOverlay(itemName);
        uiExtension.selectItem(itemName);
    }

    @And("^I search \"([^\"]*)\"$")
    public void SearchItem(String item) throws Exception {
        item = getOverlay(item);
        uiExtension.searchItem(item);
    }

    @Given("^Read Property File \"(.*?)\"$")
    public void ReadProperty(String propertyFile) throws Exception {
        uiExtension.readMovieNames(propertyFile);
    }

    @Given("^I search for movie \"(.*?)\" \"(.*?)\"$")
    public void searchMovies(String movieNo, String movieName) throws InterruptedException, IOException {
        uiExtension.searchMovie(movieNo, movieName);
    }

    @Given("^I search for movies$")
    public void searchMovies() throws InterruptedException, IOException {
        uiExtension.searchAllMovies();

    }
}
