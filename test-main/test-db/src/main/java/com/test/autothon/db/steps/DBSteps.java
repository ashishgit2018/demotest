package com.test.autothon.db.steps;

import java.sql.ResultSet;

import com.test.autothon.db.core.DBConnection;

import cucumber.api.java.en.Given;

public class DBSteps {

	private DBConnection dbc = new DBConnection();
	
	public DBSteps(){
		dbc.createDBConnection();
	}
	
	@Given ("^Execute query \"(.*?)\" $")
	public void runQuery(String sqlQuery){
		ResultSet rs = dbc.executeQuery(sqlQuery);
	}
}
