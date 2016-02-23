package org.netcracker.unc.group16.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

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
            /*
            Выставляем локаль, чтобы не выдавалась ошибка
            ORA-00604: error occurred at recursive SQL level 1
            ORA-12705: Cannot access NLS data files or invalid environment specified
             */
            Locale.setDefault(new Locale("EN","US"));
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "PIZZADB",
                    "PIZZADB");

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
