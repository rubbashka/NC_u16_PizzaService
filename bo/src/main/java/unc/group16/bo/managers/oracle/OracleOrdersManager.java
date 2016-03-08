package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Order;

import java.sql.*;


public class OracleOrdersManager extends AbstractDatabaseManager implements Manager<Order> {
    public static String TABLE_NAME = "ORDERS";
    public static String ID_COLUMN_NAME = "ORD_ID";
    
    public Long create(Order order){
        Connection con = getJDBC().getConnection();
        
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?, ?)";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, order.getClientId());
            ps.setDate(2, new Date(order.getOrderDate().getTime()));
            ps.setDate(3, new Date(order.getDeliveryDate().getTime()));
            ps.setString(4, order.getComments());

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
    
    public Order read(Long id) {
        Connection con = getJDBC().getConnection();
        
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Order order = new Order();
                order.setId(res.getLong(1));
                order.setClientId(res.getLong(2));
                order.setOrderDate(res.getDate(3));
                order.setDeliveryDate(res.getDate(4));
                order.setComments(res.getString(5));
                log.debug("Reading successful");
                
                return order;
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
    
    public boolean update(Order order) {
        Connection con = getJDBC().getConnection();
        
        String sql = "UPDATE " + TABLE_NAME + " SET CLNT_CLNT_ID=?, ORDER_DATE=?, DELIVERY_DATE=?, COMMENTS=? WHERE " + ID_COLUMN_NAME + "=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, order.getClientId());
            ps.setDate(2, new Date(order.getOrderDate().getTime()));
            ps.setDate(3, new Date(order.getDeliveryDate().getTime()));
            ps.setString(4, order.getComments());
            ps.setLong(5, order.getId());
            
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