package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.TestDemo2019;
import com.test.autothon.common.StepDefinition;
import cucumber.api.java.en.And;

public class TestDemo2019Steps extends StepDefinition {

    TestDemo2019 testDemo2019 = new TestDemo2019();

    @And("^Get movie details from the properties file \"([^\"]*)\"")
    public void getMovieDetailsFromThePropFile(String propFile) {
        testDemo2019.getAllValuesFromPropFile(propFile);
    }

    @And("Search the movies in Google")
    public void searchMoviesInGoogle() {
        testDemo2019.searchMoviesInGoogle();
    }

    @And("Assert the Wiki and IMDB Results")
    public void assertResultsWikiAndIMDB() {
        testDemo2019.searchAndAssertTest();
    }
}
