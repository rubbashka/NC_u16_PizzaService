package unc.group16.bo.managers.oracle;

import unc.group16.bo.interfaces.AbstractDatabaseManager;
import unc.group16.bo.interfaces.Manager;
import unc.group16.data.MeasurementUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OracleMeasurementUnitsManager extends AbstractDatabaseManager implements Manager<MeasurementUnit> {
    public Long create(MeasurementUnit measurementUnit){
        return getJDBC().insert(measurementUnit);
    }
    
    public MeasurementUnit read(Long id) {
        return (MeasurementUnit) getJDBC().select(new MeasurementUnit().setId(id));
    }
    
    public boolean update(MeasurementUnit measurementUnit) {
        return getJDBC().update(measurementUnit);
    }
    
    public boolean delete(Long id) {
        return getJDBC().delete(new MeasurementUnit().setId(id));
    }
}
