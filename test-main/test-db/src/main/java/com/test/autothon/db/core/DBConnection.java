package com.test.autothon.db.core;

import com.test.autothon.common.ReadPropertiesFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Rahul_Goyal
 */
public class DBConnection {

    private final static Logger logger = LogManager.getLogger(DBConnection.class);
    private static Connection connection;
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

    private Connection createDBConnection() {
        if (connection != null) {
            logger.info("Connected to DB");
            return connection;
        }

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
                tearDownDBConnection();
            }
        });

        return connection;
    }

    private void tearDownDBConnection() {
        if (connection != null)
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                logger.error("Error closing the DB connection \n" + e);
            }
    }

    public Connection getConnection() {
        return connection;
    }


}
