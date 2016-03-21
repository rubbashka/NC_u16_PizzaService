package unc.group16.bo.interfaces;

import org.apache.log4j.Logger;
import unc.group16.bo.JDBC;
import unc.group16.interfaces.TableRecord;


public abstract class AbstractDatabaseManager <T extends TableRecord> implements Manager<T>{
    private JDBC jdbc;
    public static final Logger log = Logger.getLogger(AbstractDatabaseManager.class);

    public AbstractDatabaseManager() {
        jdbc = new JDBC();
    }


    public JDBC getJDBC() {
        return jdbc;
    }

    public void setJDBC(JDBC jdbc) {
        this.jdbc = jdbc;
    }
}
