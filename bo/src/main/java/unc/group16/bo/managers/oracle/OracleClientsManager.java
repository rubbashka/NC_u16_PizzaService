package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Client;

import java.sql.*;


public class OracleClientsManager extends AbstractDatabaseManager implements Manager<Client> {
    public static String TABLE_NAME = "CLIENTS";
    public static String ID_COLUMN_NAME = "CLNT_ID";

    public Client create(Client client){
        Connection con = getJDBC().setConnection();

        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getHome());
            ps.setString(4, client.getApartment());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getComments());

            int rows = ps.executeUpdate();

            // Получение id записи
            ResultSet resSet = con.createStatement().executeQuery("SELECT MAX(" + ID_COLUMN_NAME + ") FROM " + TABLE_NAME);
            if (resSet.next() && rows > 0) {
                log.debug("Inserted successfully");
                Client result = (Client) client.clone();
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

    public Client read(Long id) {
        Connection con = getJDBC().setConnection();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Client client = new Client();
                client.setId(res.getLong(1));
                client.setName(res.getString(2));
                client.setAddress(res.getString(3));
                client.setHome(res.getString(4));
                client.setApartment(res.getString(5));
                client.setPhone(res.getString(6));
                client.setComments(res.getString(7));

                log.debug("Reading successful");
                return client;
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

    public Client update(Client client) {
        Connection con = getJDBC().setConnection();

        String sql = "UPDATE " + TABLE_NAME + " SET NAME=?, ADDRESS=?, HOME=?, APPARTMENT=?, PHONE_NUMBER=?, COMMENTS=? WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getHome());
            ps.setString(4, client.getApartment());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getComments());
            ps.setLong(7, client.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Updating successful");
                return client;
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
