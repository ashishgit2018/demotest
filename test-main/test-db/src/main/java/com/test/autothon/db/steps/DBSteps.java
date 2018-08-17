package com.test.autothon.db.steps;

import com.test.autothon.common.StepDefinition;
import com.test.autothon.db.core.DBQueryUtils;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

import java.sql.ResultSet;

/**
 * @author Rahul_Goyal
 */
public class DBSteps extends StepDefinition {

    private DBQueryUtils dbu;

    public DBSteps() {
        dbu = new DBQueryUtils();
    }

    @Given("^Validate the select query \"(.*?)\"$")
    public void runSelectQuery(String sqlQuery, DataTable dataTable) {
        String headers = "";
        /*if (dataTable != null){
            headers = dataTable.raw().get(0).toString().replaceAll("^\\[|\\]$","").toLowerCase();
        }*/
        sqlQuery = getOverlay(sqlQuery);
        ResultSet rs = dbu.executeQuery(sqlQuery);
        dbu.validateDataTableDataWithResultSet(dataTable, rs);
    }

    @Given("^Execute the select query \"(.*?)\"$")
    public void runSelectQuery(String sqlQuery) {
        sqlQuery = getOverlay(sqlQuery);
        ResultSet rs = dbu.executeQuery(sqlQuery);
        dbu.getResultSetAsList(rs);
    }

    @Given("^Execute the update query \"(.*?)\"$")
    public void runUpdateQuery(String sqlQuery) {
        sqlQuery = getOverlay(sqlQuery);
        dbu.updateQuery(sqlQuery);
    }
}
