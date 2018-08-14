package com.test.autothon.db.steps;

import com.test.autothon.common.StepDefinition;
import com.test.autothon.db.core.DBConnection;
import cucumber.api.java.en.Given;

import java.sql.ResultSet;

public class DBSteps extends StepDefinition {

    private DBConnection dbc = new DBConnection();

    public DBSteps() {
        //dbc.createDBConnection();
    }

    @Given("^Execute query \"(.*?)\"$")
    public void runQuery(String sqlQuery) {
        sqlQuery = getOverlay(sqlQuery);
        ResultSet rs = dbc.executeQuery(sqlQuery);
        dbc.getResultSetAsList(rs);
    }
}
