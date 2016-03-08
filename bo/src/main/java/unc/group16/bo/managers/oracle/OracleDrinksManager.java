package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.Drink;


public class OracleDrinksManager extends AbstractDatabaseManager implements Manager<Drink> {
    public Long create(Drink drink){
        return getJDBC().insert(drink);
    }

    public Drink read(Long id) {
        return (Drink) getJDBC().select(new Drink().setId(id));
    }

    public boolean update(Drink drink) {
        return getJDBC().update(drink);
    }

    public boolean delete(Long id) {
        return getJDBC().delete(new Drink().setId(id));
    }
}
