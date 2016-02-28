package unc.group16.bo.interfaces;

import unc.group16.bo.JDBC;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class DatabaseManager {
    private JDBC jdbc;

    public DatabaseManager() {
        jdbc = new JDBC();
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Closing connection failed.");
                e.printStackTrace();
            }
        }
    }

    protected JDBC getJDBC() {
        return jdbc;
    }
}
