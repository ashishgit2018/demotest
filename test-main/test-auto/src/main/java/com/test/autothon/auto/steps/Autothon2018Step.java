package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.Autothon2018;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import java.io.IOException;

public class Autothon2018Step extends StepDefinition {

    Autothon2018 autothon2018 = new Autothon2018();

    @And("^I select \"([^\"]*)\"$")
    public void BuyItem(String itemName) throws Exception {
        itemName = getOverlay(itemName);
        autothon2018.selectItem(itemName);
    }

    @And("^I search \"([^\"]*)\"$")
    public void SearchItem(String item) throws Exception {
        item = getOverlay(item);
        autothon2018.searchItem(item);
    }

    @Given("^Read Property File \"(.*?)\"$")
    public void ReadProperty(String propertyFile) throws Exception {
        autothon2018.readMovieNames(propertyFile);
    }

    @Given("^I search for movie \"(.*?)\" \"(.*?)\"$")
    public void searchMovies(String movieNo, String movieName) throws InterruptedException, IOException {
        autothon2018.searchMovie(movieNo, movieName);
    }

    @Given("^I search for movies$")
    public void searchMovies() throws InterruptedException, IOException {
        autothon2018.searchAllMovies();

    }
}