package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Drink;

import java.sql.*;


public class OracleDrinksManager extends AbstractDatabaseManager implements Manager<Drink> {
    public static String TABLE_NAME = "DRINKS";
    public static String ID_COLUMN_NAME = "DRNK_ID";

    public Drink create(Drink drink){
        Connection con = getJDBC().setConnection();

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, drink.getVolume());
            ps.setBigDecimal(2, drink.getPrice());
            ps.setString(3, drink.getName());
            ps.setString(4, drink.getComments());

            int rows = ps.executeUpdate();

            // Получение id записи
            ResultSet resSet = con.createStatement().executeQuery("SELECT MAX(" + ID_COLUMN_NAME + ") FROM " + TABLE_NAME + "");
            if (resSet.next() && rows > 0) {
                log.debug("Inserted successfully");
                Drink result = (Drink) drink.clone();
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

    public Drink read(Long id) {
        Connection con = getJDBC().setConnection();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Drink drink = new Drink();
                drink.setId(res.getLong(1));
                drink.setVolume(res.getInt(2));
                drink.setPrice(res.getBigDecimal(3));
                drink.setName(res.getString(4));
                drink.setComments(res.getString(5));
                log.debug("Reading successful");

                return drink;
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

    public Drink update(Drink drink) {
        Connection con = getJDBC().setConnection();

        String sql = "UPDATE " + TABLE_NAME + " SET VOLUME=?, PRICE=?, NAME=?, COMMENTS=? WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, drink.getVolume());
            ps.setBigDecimal(2, drink.getPrice());
            ps.setString(3, drink.getName());
            ps.setString(4, drink.getComments());
            ps.setLong(5, drink.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Updating successful");
                return drink;
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
