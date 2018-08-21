package com.test.autothon.db.core;

import com.test.autothon.common.StepDefinition;
import cucumber.api.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Rahul_Goyal
 */
public class DBQueryUtils extends StepDefinition {

    private final static Logger logger = LogManager.getLogger(DBQueryUtils.class);
    private Connection connection;

    public DBQueryUtils() {
        DBConnection dbc = new DBConnection();
        connection = dbc.getConnection();
    }

    //Execute Select Query Statement
    public ResultSet executeQuery(String sqlQuery) {
        logger.info("Executing Query : " + sqlQuery);
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            logger.error("Error creating the Execute Query Statement \n" + e);
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sqlQuery);
        } catch (SQLException e) {
            logger.error("Error executing the Execute Query Statement \n" + e);
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
            logger.error("Error creating the Update Query Statement \n" + e);
        }
        int returnCode = 0;
        try {
            returnCode = stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            logger.error("Error executing the Update Query Statement \n" + e);
        }
        logger.info("Update query return code is : " + returnCode);
        return returnCode;
    }


    //Returns a List of Map
    public List<SortedMap<String, String>> getResultSetAsList(ResultSet rs) {
        if (rs == null)
            return null;
        List<String> rsHeaders = new ArrayList<String>();
        List<SortedMap<String, String>> rsdata = new ArrayList<SortedMap<String, String>>();
        ResultSetMetaData rsmd = null;
        int noOfColumns = 0;
        try {
            rsmd = rs.getMetaData();
            noOfColumns = rsmd.getColumnCount();

            while (rs.next()) {
                SortedMap<String, String> rowData = new TreeMap<String, String>();
                for (int i = 1; i <= noOfColumns; i++) {
                    String data = "";
                    if (rsmd.getColumnType(i) == Types.DATE)
                        data = rs.getDate(i).toString();
                    else if (rsmd.getColumnType(i) == Types.TIMESTAMP)
                        data = rs.getTimestamp(i).toString();
                    else
                        data = rs.getString(i);
                    if (data.equals("") || data == null)
                        data = "null";
                    rowData.put(rsmd.getColumnName(i).toUpperCase(), data.toUpperCase());
                }
                rsdata.add(rowData);
            }
            logger.info("Total Rows from DB : " + rsdata.size() + "\nResultSet data from DB : \n" + rsdata.toString().replaceAll("}, \\{", "}\n {"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsdata;
    }

    public List<SortedMap<String, String>> getDataSetAsList(DataTable data) {
        if (data == null)
            return null;
        List<SortedMap<String, String>> sortedData = new ArrayList<SortedMap<String, String>>();
        List<List<String>> rowData = data.raw();
        for (List<String> lData : rowData.subList(1, rowData.size())) {
            SortedMap<String, String> sData = new TreeMap<>();
            for (int iC = 0; iC < rowData.get(0).size(); iC++) {
                String value = getOverlay(lData.get(iC));
                if (value.equals("") || value == null)
                    value = "null";
                sData.put(rowData.get(0).get(iC).toUpperCase(), value.toUpperCase());
            }
            sortedData.add(sData);
        }
        logger.info("Total Rows from DataTable : " + sortedData.size() + "\nDataTable data from DB : \n" + sortedData.toString().replaceAll("}, \\{", "}\n {"));
        return sortedData;
    }


    public void validateDataTableDataWithResultSet(DataTable data, ResultSet rs) {
        List<SortedMap<String, String>> rsData = getResultSetAsList(rs);
        List<SortedMap<String, String>> tableData = getDataSetAsList(data);

        if (rsData == null && tableData == null)
            return;
        else if (rsData == null && tableData != null || rsData != null && tableData == null) {
            Assert.fail("DataTable or ResultSet object might be NULL");
        }

        int tableDataSize = tableData.size();
        int rsDataSize = rsData.size();

        if (tableDataSize != rsDataSize) {
            Assert.fail("Expected " + tableDataSize + " records from DataTable but got " + rsDataSize + " records from the database");
            return;
        }

        if (!rsData.equals(tableData))
            Assert.fail("Expected DataTale Data : \n" + tableData.toString().replaceAll("}, \\{", "}\n {") + "\nActual ResultSet Data : \n" + rsData.toString().replaceAll("}, \\{", "}\n {"));


    }

}
