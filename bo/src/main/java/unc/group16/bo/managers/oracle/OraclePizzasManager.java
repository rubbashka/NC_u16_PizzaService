package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Pizza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OraclePizzasManager extends AbstractDatabaseManager implements Manager<Pizza> {
    public static String TABLE_NAME = "PIZZAS";
    public static String ID_COLUMN_NAME = "PZ_ID";
    
    public Pizza create(Pizza pizza){
        Connection con = getJDBC().setConnection();
        
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES (null, ?, ?, ?, ?, ?)";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, pizza.getName());
            ps.setString(2, pizza.getType());
            ps.setInt(3, pizza.getWeight());
            ps.setBigDecimal(4, pizza.getPrice());
            ps.setString(5, pizza.getComments());
            
            int rows = ps.executeUpdate();
            
            // Получение id записи
            ResultSet resSet = con.createStatement().executeQuery("SELECT MAX(" + ID_COLUMN_NAME + ") FROM " + TABLE_NAME + "");
            if (resSet.next() && rows > 0) {
                log.debug("Inserted successfully");
                Pizza result = (Pizza) pizza.clone();
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
    
    public Pizza read(Long id) {
        Connection con = getJDBC().setConnection();
        
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Pizza pizza = new Pizza();
                pizza.setId(res.getLong(1));
                pizza.setName(res.getString(2));
                pizza.setType(res.getString(3));
                pizza.setWeight(res.getInt(4));
                pizza.setPrice(res.getBigDecimal(5));
                pizza.setComments(res.getString(6));
                log.debug("Reading successful");
                
                return pizza;
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
    
    public Pizza update(Pizza pizza) {
        Connection con = getJDBC().setConnection();
        
        String sql = "UPDATE " + TABLE_NAME + " SET NAME=?, TYPE=?, WHEIGHT=?, PRICE=?, COMMENTS=? WHERE " + ID_COLUMN_NAME + "=?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, pizza.getName());
            ps.setString(2, pizza.getType());
            ps.setInt(3, pizza.getWeight());
            ps.setBigDecimal(4, pizza.getPrice());
            ps.setString(5, pizza.getComments());
            ps.setLong(6, pizza.getId());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Updating successful");
                return pizza;
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
