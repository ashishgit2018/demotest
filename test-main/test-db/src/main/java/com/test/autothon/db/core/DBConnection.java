package com.test.autothon.db.core;

import com.test.autothon.common.ReadPropertiesFile;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBConnection {

    private final static Logger logger = Logger.getLogger(DBConnection.class);
    public static Connection connection;
    private String dbUserName;
    private String dbPassword;
    private String dbUrl;
    private String dbClassName;

    public DBConnection() {
        this.dbClassName = ReadPropertiesFile.getPropertyValue("db.classname");
        this.dbUrl = ReadPropertiesFile.getPropertyValue("db.url");
        this.dbUserName = ReadPropertiesFile.getPropertyValue("db.username");
        this.dbPassword = ReadPropertiesFile.getPropertyValue("db.password");
        createDBConnection();
    }

    public Connection createDBConnection() {
        if (connection != null)
            return connection;

        if (dbUrl == null | dbUserName == null | dbPassword == null) {
            logger.error("Set the DB Connection Parameters");
            return null;
        }

        logger.info("Connection to DB started .....");
        try {
            Class.forName(dbClassName);
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
    public ResultSet executeQuery(String sqlQuery) {
        logger.info("Executing Query : " + sqlQuery);
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
    public int updateQuery(String sqlQuery) {
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
    public List<Map<String, String>> getResultSetAsList(ResultSet rs) {
        List<String> rsHeaders = new ArrayList<String>();
        List<Map<String, String>> rsdata = new ArrayList<Map<String, String>>();
        ResultSetMetaData rsmd = null;
        int noOfColumns = 0;
        try {
            rsmd = rs.getMetaData();
            noOfColumns = rsmd.getColumnCount();

            for (int i = 1; i <= noOfColumns; i++) {
                rsHeaders.add(rsmd.getColumnName(i));
            }

            while (rs.next()) {
                Map<String, String> rowData = new HashMap<String, String>();
                for (String header : rsHeaders) {
                    String data = rs.getString(header);
                    rowData.put(header, data);
                }
                rsdata.add(rowData);
            }
            logger.info("Result set data : \n" + rsdata.toString());
            return rsdata;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
