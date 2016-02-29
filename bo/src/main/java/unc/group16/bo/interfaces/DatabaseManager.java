package unc.group16.bo.interfaces;

import org.apache.log4j.Logger;
import unc.group16.bo.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public abstract class DatabaseManager {
    private JDBC jdbc;
    public static final Logger log = Logger.getLogger(DatabaseManager.class);

    public static String TABLE_NAME;

    public DatabaseManager() {
        jdbc = new JDBC();
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("An error occurred while closing the connection", e);
            }
        }
    }

    protected JDBC getJDBC() {
        return jdbc;
    }

    protected void delete(String tableName, String keyColumn, Long id) {
        Connection conn = getJDBC().setConnection();

        String sql = "DELETE FROM " + tableName + " WHERE " + keyColumn + " = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Deleting successful");
            } else {
                log.error("Unable to find a record with id " + id);
            }
        }
        catch (SQLException e) {
            log.error("Deleting failed", e);
        }
        finally {
            closeConnection(conn);
        }
    }
}
