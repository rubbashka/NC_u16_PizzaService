package unc.group16.bo.managers;

import unc.group16.bo.interfaces.DatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Sauce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SaucesManager extends DatabaseManager implements Manager<Sauce> {
    public static String TABLE_NAME = "SAUCES";
    public static String ID_COLUMN_NAME = "SC_ID";

    public Sauce create(Sauce sauce){
        Connection con = getJDBC().setConnection();

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, sauce.getPrice());
            ps.setString(2, sauce.getName());
            ps.setString(3, sauce.getComments());

            int rows = ps.executeUpdate();

            // Получение id записи
            ResultSet resSet = con.createStatement().executeQuery("SELECT MAX(" + ID_COLUMN_NAME + ") FROM " + TABLE_NAME + "");
            if (resSet.next() && rows > 0) {
                log.debug("Inserted successfully");
                Sauce result = (Sauce) sauce.clone();
                result.setId(resSet.getLong(1));
                return result;
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

    public Sauce read(Long id) {
        Connection con = getJDBC().setConnection();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Sauce sauce = new Sauce();
                sauce.setId(res.getLong(1));
                sauce.setPrice(res.getBigDecimal(2));
                sauce.setName(res.getString(3));
                sauce.setComments(res.getString(4));
                log.debug("Reading successful");

                return sauce;
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

    public Sauce update(Sauce sauce) {
        Connection con = getJDBC().setConnection();

        String sql = "UPDATE " + TABLE_NAME + " SET PRICE=?, NAME=?, COMMENTS=? WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, sauce.getPrice());
            ps.setString(2, sauce.getName());
            ps.setString(3, sauce.getComments());
            ps.setLong(4, sauce.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Updating successful");
                return sauce;
            }

        }
        catch (SQLException e) {
            log.error("Updating failed", e);
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    public void delete(Long id) {
        delete(TABLE_NAME, ID_COLUMN_NAME, id);
    }
}
