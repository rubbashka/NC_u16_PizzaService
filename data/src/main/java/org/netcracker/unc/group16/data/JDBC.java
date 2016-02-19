package org.netcracker.unc.group16.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Ivan.Chikhanov on 19.02.2016.
 */


public class JDBC {
    public Connection setConnection(){
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found");
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@srv2-ora18.net.billing.ru:1521:smrsupp", "IBR_TEST",
                    "employer");

        } catch (SQLException e) {

            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("Connected");
        } else {
            System.out.println("Failed to make connection");
        }
        return connection;
    }
}
