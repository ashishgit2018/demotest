package com.test.autothon.db.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class DBConnection {
	
	private String dbUserName;
	private String dbPassword;
	private String dbUrl;
	public static Connection connection;

	public DBConnection() {
		this.dbUserName = "";
		this.dbPassword = "";
		this.dbUrl = "";
	}
	
	private final static Logger logger = Logger.getLogger(DBConnection.class);
	
	public Connection createDBConnection(){
		if (connection != null)
			return connection;
		
		if (dbUrl == null | dbUserName == null | dbPassword == null){
			logger.error("Set the DB Connection Parameters");
			return null;
		}
		
		logger.info("Connection to DB started .....");
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (connection != null)
			logger.info("DB Connection Established !!");
		else
			logger.error("DB connection Failed !!");
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				logger.info("Running the DB Shutdown Hook ...");
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
			
		return connection;
	}
	
	
	//Execute Select Query Statement
	public ResultSet executeQuery (String sqlQuery) {
		logger.info("Executing Query : " +  sqlQuery);
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//Execute Update/Delete Query Statement
	public int updateQuery(String sqlQuery){
		logger.info("Executing update query : " + sqlQuery);
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int returnCode = 0;
		try {
			returnCode = stmt.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnCode;
	}
	
	
	//Returns a List of Map
	public List<Map<String,String>> getResultSetAsList(ResultSet rs) throws SQLException{
		List<String> rsHeaders = new ArrayList<String>();
		List<Map<String, String>> rsdata = new ArrayList<Map<String,String>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int noOfColumns = rsmd.getColumnCount();
		
		for(int i = 1; i <= noOfColumns; i++){
			rsHeaders.add(rsmd.getCatalogName(i));
		}
		
		while (rs.next()){
			Map<String, String> rowData = new HashMap<String, String>();
			for (String header : rsHeaders){
				String data = rs.getString(header);
				rowData.put(header, data);
			}
			rsdata.add(rowData);
		}		
		return rsdata;
	}

}
