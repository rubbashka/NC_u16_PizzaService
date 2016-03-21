package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.data.Pizza;

import javax.ejb.Stateless;

@Stateless
public class OraclePizzasManager extends AbstractDatabaseManager<Pizza> {
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
