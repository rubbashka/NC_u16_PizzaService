package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.MeasurementUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OracleMeasurementUnitsManager extends AbstractDatabaseManager implements Manager<MeasurementUnit> {
    public static String TABLE_NAME = "MEASUREMENT_UNITS";
    public static String ID_COLUMN_NAME = "MSRU_ID";

    public Long create(MeasurementUnit mu){
        Connection con = getJDBC().getConnection();

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mu.getName());

            int rows = ps.executeUpdate();

            // Получение id записи
            ResultSet resSet = con.createStatement().executeQuery("SELECT MAX(" + ID_COLUMN_NAME + ") FROM " + TABLE_NAME + "");
            if (resSet.next() && rows > 0) {
                log.debug("Inserted successfully");
                return resSet.getLong(1);
            }
        }
        catch (SQLException e) {
            log.error("Inserting failed", e);
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    public MeasurementUnit read(Long id) {
        Connection con = getJDBC().getConnection();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                MeasurementUnit mu = new MeasurementUnit();
                mu.setId(res.getLong(1));
                mu.setName(res.getString(2));
                log.debug("Reading successful");

                return mu;
            }
        }
        catch (SQLException e) {
            log.error("Reading failed", e);
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    public boolean update(MeasurementUnit mu) {
        Connection con = getJDBC().getConnection();

        String sql = "UPDATE " + TABLE_NAME + " SET NAME=?, COMMENTS=? WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, mu.getName());
            ps.setLong(2, mu.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Updating successful");
                return true;
            }

        }
        catch (SQLException e) {
            log.error("Updating failed", e);
        }
        finally {
            closeConnection(con);
        }

        return false;
    }

    public boolean delete(Long id) {
        return delete(TABLE_NAME, ID_COLUMN_NAME, id);
    }
}
