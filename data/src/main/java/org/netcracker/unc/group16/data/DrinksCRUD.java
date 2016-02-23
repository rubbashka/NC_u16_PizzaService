package org.netcracker.unc.group16.data;

import java.sql.*;


public class DrinksCRUD {

    private JDBC jdbc;

    public DrinksCRUD() {
        jdbc = new JDBC();
    }

    public void create(int volume, int price, String def, String comments){
        Connection con = jdbc.setConnection();

        String sql = "INSERT INTO DRINKS (VOLUME, PRICE, DEF, COMMENTS) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, volume);
            ps.setInt(2, price);
            ps.setString(3, def);
            ps.setString(4, comments);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Inserted into DRINKS successfully.");
            }
        }
        catch (SQLException e) {
            System.out.println("Inserting failed.");
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }
    }

    public Drink read(int id) {
        Connection con = jdbc.setConnection();

        String sql = "SELECT * FROM DRINKS WHERE DRNK_ID=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Drink drink = new Drink();
                drink.setVolume(res.getInt(2));
                drink.setPrice(res.getInt(3));
                drink.setDef(res.getString(4));
                drink.setComments(res.getString(5));
                System.out.println("Reading successful.");

                return drink;
            }
        }
        catch (SQLException e) {
            System.out.println("Reading failed.");
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }

        return null;
    }

    public void update(Drink drink) {
        Connection con = jdbc.setConnection();

        String sql = "UPDATE DRINKS SET VOLUME=?, PRICE=?, DEF=?, COMMENTS=? WHERE DRNK_ID=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, drink.getVolume());
            ps.setInt(2, drink.getPrice());
            ps.setString(3, drink.getDef());
            ps.setString(4, drink.getComments());
            ps.setInt(5, drink.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Updating successful.");
            }

        }
        catch (SQLException e) {
            System.out.println("Updating failed.");
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }
    }

    public void delete(int drinkId) {
        Connection conn = jdbc.setConnection();

        String sql = "DELETE FROM DRINKS WHERE DRNK_ID = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, drinkId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Deleting successful.");
            } else {
                System.out.println("Nothing to delete.");
            }
        }
        catch (SQLException e) {
            System.out.println("Deleting failed.");
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Closing connection failed.");
                e.printStackTrace();
            }
        }
    }


}
