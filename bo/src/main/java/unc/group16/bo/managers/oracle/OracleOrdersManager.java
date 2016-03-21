package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.data.Order;

import javax.ejb.Stateless;

@Stateless
public class OracleOrdersManager extends AbstractDatabaseManager<Order> {
    public Long create(Order order){
        return getJDBC().insert(order);
    }
    
    public Order read(Long id) {
        return (Order) getJDBC().select(new Order().setId(id));
    }
    
    public boolean update(Order order) {
        return getJDBC().update(order);
    }
    
    public boolean delete(Long id) {
        return getJDBC().delete(new Order().setId(id));
    }
}