package com.test.autothon.auto.steps;

import com.test.autothon.auto.core.Demo2019;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

public class DemoSteps {

    Demo2019 demo2019 = new Demo2019();

    @Given("The no. of movies")
    public void moviesCount(DataTable table) {
        demo2019.createMovieList(Integer.parseInt(table.asList(String.class).get(0)));
    }
}