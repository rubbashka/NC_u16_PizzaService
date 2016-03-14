package unc.group16.bo.interfaces;

import org.apache.log4j.Logger;
import unc.group16.bo.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public abstract class AbstractDatabaseManager {
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
