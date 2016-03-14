package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Ingredient;

import java.sql.*;


public class OracleIngredientsManager extends AbstractDatabaseManager implements Manager<Ingredient> {
    public Long create(Ingredient ingredient){
        return getJDBC().insert(ingredient);
    }
    
    public Ingredient read(Long id) {
        return (Ingredient) getJDBC().select(new Ingredient().setId(id));
    }
    
    public boolean update(Ingredient ingredient) {
        return getJDBC().update(ingredient);
    }
    
    public boolean delete(Long id) {
        return getJDBC().delete(new Ingredient().setId(id));
    }
}