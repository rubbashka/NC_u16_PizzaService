package unc.group16.bo.managers;

import unc.group16.bo.interfaces.DatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Drink;

import java.sql.*;


public class DrinksManager extends DatabaseManager implements Manager<Drink> {

    public Drink create(Drink drink){
        Connection con = getJDBC().setConnection();

        String sql = "INSERT INTO DRINKS (VOLUME, PRICE, DEF, COMMENTS) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, drink.getVolume());
            ps.setInt(2, drink.getPrice());
            ps.setString(3, drink.getDef());
            ps.setString(4, drink.getComments());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Inserted into DRINKS successfully");
                return drink;
            }
        }
        catch (SQLException e) {
            log.error("Inserting failed");
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    public Drink read(Long id) {
        Connection con = getJDBC().setConnection();

        String sql = "SELECT * FROM DRINKS WHERE DRNK_ID=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Drink drink = new Drink();
                drink.setVolume(res.getInt(2));
                drink.setPrice(res.getInt(3));
                drink.setDef(res.getString(4));
                drink.setComments(res.getString(5));
                log.debug("Reading successful");

                return drink;
            }
        }
        catch (SQLException e) {
            log.error("Reading failed");
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    public Drink update(Drink drink) {
        Connection con = getJDBC().setConnection();

        String sql = "UPDATE DRINKS SET VOLUME=?, PRICE=?, DEF=?, COMMENTS=? WHERE DRNK_ID=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, drink.getVolume());
            ps.setInt(2, drink.getPrice());
            ps.setString(3, drink.getDef());
            ps.setString(4, drink.getComments());
            ps.setLong(5, drink.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Updating successful");
                return drink;
            }

        }
        catch (SQLException e) {
            log.error("Updating failed");
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    public void delete(Long id) {
        Connection conn = getJDBC().setConnection();

        String sql = "DELETE FROM DRINKS WHERE DRNK_ID = ?";
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
            log.error("Deleting failed");
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }
}
