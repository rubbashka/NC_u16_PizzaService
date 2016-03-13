package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Sauce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OracleSaucesManager extends AbstractDatabaseManager implements Manager<Sauce> {
    public Long create(Sauce sauce){
        return getJDBC().insert(sauce);
    }
    
    public Sauce read(Long id) {
        return (Sauce) getJDBC().select(new Sauce().setId(id));
    }
    
    public boolean update(Sauce sauce) {
        return getJDBC().update(sauce);
    }
    
    public boolean delete(Long id) {
        return getJDBC().delete(new Sauce().setId(id));
    }
}
