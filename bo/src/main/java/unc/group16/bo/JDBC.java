package unc.group16.bo;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class JDBC {
    public static final Logger log = Logger.getLogger(JDBC.class);

    public Connection setConnection(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            log.error("Oracle JDBC Driver not found");
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            /*
            Выставляем локаль, чтобы не выдавалась ошибка
            ORA-00604: error occurred at recursive SQL level 1
            ORA-12705: Cannot access NLS data files or invalid environment specified
             */
            Locale.setDefault(new Locale("EN","US"));
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "PIZZADB", "PIZZADB");
        } catch (SQLException e) {
            log.error("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            log.debug("Connected");
        } else {
            log.error("Failed to make connection");
        }
        return connection;
    }
}
