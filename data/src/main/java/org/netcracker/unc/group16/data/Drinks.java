package org.netcracker.unc.group16.data;

import java.sql.*;


public class Drinks {
    private int volume;
    private int price;
    private String def;
    private String comments;

    private JDBC jdbc;

    public Drinks() {
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
                this.volume =  volume;
                this.price = price;
                this.def = def;
                this.comments = comments;

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

    public void read(int drinkId) {
        Connection con = jdbc.setConnection();

        String sql = "SELECT * FROM DRINKS WHERE DRNK_ID=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, drinkId);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                volume = res.getInt(2);
                price = res.getInt(3);
                def = res.getString(4);
                comments = res.getString(5);
                System.out.println("Reading successful.");
            }

        }
        catch (SQLException e) {
            System.out.println("Reading failed.");
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }
    }

    public void update(int drinkId) {
        Connection con = jdbc.setConnection();

        String sql = "UPDATE DRINKS SET VOLUME=?, PRICE=?, DEF=?, COMMENTS=? WHERE DRNK_ID=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, volume);
            ps.setInt(2, price);
            ps.setString(3, def);
            ps.setString(4, comments);
            ps.setInt(5, drinkId);

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


    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
