package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Pizza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OraclePizzasManager extends AbstractDatabaseManager implements Manager<Pizza> {
    public Long create(Pizza pizza){
        return getJDBC().insert(pizza);
    }
    
    public Pizza read(Long id) {
        return (Pizza) getJDBC().select(new Pizza().setId(id));
    }
    
    public boolean update(Pizza pizza) {
        return getJDBC().update(pizza);
    }
    
    public boolean delete(Long id) {
        return getJDBC().delete(new Pizza().setId(id));
    }
}
