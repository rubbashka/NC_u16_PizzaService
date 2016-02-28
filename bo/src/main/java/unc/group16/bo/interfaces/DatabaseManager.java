package unc.group16.bo.interfaces;

import org.apache.log4j.Logger;
import unc.group16.bo.JDBC;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class DatabaseManager {
    private JDBC jdbc;
    public static final Logger log = Logger.getLogger(DatabaseManager.class);

    public DatabaseManager() {
        jdbc = new JDBC();
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("An error occurred while closing the connection");
                e.printStackTrace();
            }
        }
    }

    protected JDBC getJDBC() {
        return jdbc;
    }
}
