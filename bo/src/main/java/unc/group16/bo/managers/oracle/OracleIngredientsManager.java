package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Ingredient;

import java.sql.*;


public class OracleIngredientsManager extends AbstractDatabaseManager implements Manager<Ingredient> {
    public static String TABLE_NAME = "INGREDIENTS";
    public static String ID_COLUMN_NAME = "INGRD_ID";

    public Long create(Ingredient ingredient){
        Connection con = getJDBC().getConnection();

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ingredient.getName());
            ps.setString(2, ingredient.getComments());

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

    public Ingredient read(Long id) {
        Connection con = getJDBC().getConnection();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(res.getLong(1));
                ingredient.setName(res.getString(2));
                ingredient.setComments(res.getString(3));
                log.debug("Reading successful");

                return ingredient;
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

    public boolean update(Ingredient ingredient) {
        Connection con = getJDBC().getConnection();

        String sql = "UPDATE " + TABLE_NAME + " SET NAME=?, COMMENTS=? WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ingredient.getName());
            ps.setString(2, ingredient.getComments());
            ps.setLong(3, ingredient.getId());

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