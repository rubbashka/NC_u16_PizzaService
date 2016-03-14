package unc.group16.bo;

import org.apache.log4j.Logger;
import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Locale;

@SuppressWarnings("Duplicates")
public class JDBC {
    public static final Logger log = Logger.getLogger(JDBC.class);

    private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private static String user = "PIZZADB";
    private static String password = "PIZZADB";


    public Connection getConnection(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            log.fatal("Oracle JDBC Driver not found", e);
        }

        Connection connection = null;
        try {
            /*
            Выставляем локаль, чтобы не выдавалась ошибка
            ORA-00604: error occurred at recursive SQL level 1
            ORA-12705: Cannot access NLS data files or invalid environment specified
             */
            Locale.setDefault(new Locale("EN","US"));
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            log.error("Unable to connect", e);
        }

        if (connection != null) {
            log.debug("Connected");
        } else {
            log.error("Unable to connect");
        }
        return connection;
    }


    public Long insert(TableRecord record) {
        Long result = -1L;

        try (Connection connection = getConnection()) {
            result = insert(record, connection);
        } catch (SQLException e) {
            log.error("Inserting failed", e);
        }

        return result;
    }

    public Long insert(TableRecord record, Connection con) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);

        String sql = "INSERT INTO " + table.name() + " VALUES (null" + new String(new char[table.columns()-1]).replace("\0", ", ?") + ")";

        try ( PreparedStatement ps = con.prepareStatement(sql, new int[] {1}) ) {

            Field[] fields = record.getClass().getDeclaredFields();
            for (Field field : fields) {
                Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
                if (columnAnnotation == null || columnAnnotation.isKey()) {
                    continue;
                }

                try {
                    field.setAccessible(true);
                    ps.setObject(columnAnnotation.id() - 1, field.get(record));
                } catch (IllegalAccessException ignored) {}
            }

            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Inserted successfully");

                // Получение id записи
                try ( ResultSet generatedKeys = ps.getGeneratedKeys() ) {
                    if (generatedKeys != null && generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    } else {
                        log.error("Unable to return id");
                    }
                } catch (SQLException e) {
                    log.error("Unable to return id", e);
                }
            }
        } catch (SQLException e) {
            log.error("Inserting failed", e);
        }

        return -1L;
    }


    public TableRecord select(TableRecord record) {
        TableRecord result = null;

        try (Connection connection = getConnection()) {
            result = select(record, connection);
        } catch (SQLException e) {
            log.error("Selecting failed", e);
        }

        return result;
    }

    public TableRecord select(TableRecord record, Connection con) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);

        StringBuilder sql = new StringBuilder("SELECT * FROM ")
                .append(table.name())
                .append(" WHERE");

        int keysCnt = 0;
        Field[] fields = record.getClass().getDeclaredFields();
        for (Field field : fields) {
            Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
            if (columnAnnotation == null || !columnAnnotation.isKey()) {
                continue;
            }

            try {
                field.setAccessible(true);
                if (keysCnt >= 1) {
                    sql.append(" AND");
                }
                sql.append(" ")
                        .append(columnAnnotation.name())
                        .append("=")
                        .append(field.get(record));
            } catch (IllegalAccessException ignored) {}

            keysCnt++;
        }

        try ( PreparedStatement ps = con.prepareStatement(sql.toString());
              ResultSet rs = ps.executeQuery() ) {
            TableRecord result = record.getClass().newInstance();
            if (rs == null || !rs.next()) {
                log.error("Unable to find a record");
                return null;
            }

            for (int i = 1; i <= table.columns(); i++) {
                for (Field field : fields) {
                    Column columnAnnotation = field.getDeclaredAnnotation(Column.class);

                    if (columnAnnotation != null && columnAnnotation.id() == i) {
                        field.setAccessible(true);

                        Object data;
                        if (field.getType() == Long.class) {
                            data = rs.getLong(columnAnnotation.id());
                        } else if (field.getType() == Integer.class) {
                            data = rs.getInt(columnAnnotation.id());
                        } else if (field.getType() == Double.class) {
                            data = rs.getDouble(columnAnnotation.id());
                        } else {
                            data = rs.getObject(columnAnnotation.id());
                        }
                        field.set(result, data);
                        break;
                    }
                }
            }
            log.debug("Selected successfully");
            return result;

        } catch (SQLException e) {
            log.error("Selecting failed", e);
        } catch (IllegalAccessException e) {
            log.error("Selecting failed: illegal access", e);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean update(TableRecord record) {
        boolean result = false;

        try (Connection connection = getConnection()) {
            result = update(record, connection);
        } catch (SQLException e) {
            log.error("Updating failed", e);
        }

        return result;
    }

    public boolean update(TableRecord record, Connection con) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);

        StringBuilder sql = new StringBuilder("UPDATE ")
                .append(table.name())
                .append(" SET");
        StringBuilder whereStatement = new StringBuilder(" WHERE");

        Field[] fields = record.getClass().getDeclaredFields();
        int colCnt = 0;
        int keysCnt = 0;
        for (Field field : fields) {
            Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
            if (columnAnnotation == null) {
                continue;
            }

            try {
                field.setAccessible(true);
                if (!columnAnnotation.isKey()) {
                    if (field.get(record) == null) {
                        continue;
                    }
                    if (colCnt >= 1) {
                        sql.append(",");
                    }
                    sql.append(" ")
                            .append(columnAnnotation.name())
                            .append("=?");
                    colCnt++;
                } else {
                    if (keysCnt >= 1) {
                        sql.append(" AND");
                    }
                    whereStatement.append(" ")
                            .append(columnAnnotation.name())
                            .append("=")
                            .append(field.get(record));
                    keysCnt++;
                }

            } catch (IllegalAccessException ignored) {}
        }
        if (colCnt == 0) {
            log.error("At least one non key argument must be not null");
            return false;
        }
        if (keysCnt == 0) {
            log.error("At least one key argument must be not null");
            return false;
        }
        sql.append(whereStatement);

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int i = 0;
            for (Field field : fields) {
                Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
                if (columnAnnotation == null || columnAnnotation.isKey() || field.get(record) == null) {
                    continue;
                }
                i++;
                ps.setObject(i, field.get(record));
            }
            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Updating successful");
                return true;
            } else {
                log.error("Unable to find a record");
            }
        }
        catch (SQLException e) {
            log.error("Updating failed", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean delete(TableRecord record) {
        boolean result = false;

        try (Connection connection = getConnection()) {
            result = delete(record, connection);
        } catch (SQLException e) {
            log.error("Deleting failed", e);
        }

        return result;
    }

    public boolean delete(TableRecord record, Connection con) {
        Table table = record.getClass().getDeclaredAnnotation(Table.class);

        StringBuilder sql = new StringBuilder("DELETE FROM ")
                .append(table.name())
                .append(" WHERE");
        Field[] fields = record.getClass().getDeclaredFields();
        int keysCnt = 0;
        for (Field field : fields) {
            Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
            if (columnAnnotation == null || !columnAnnotation.isKey()) {
                continue;
            }

            try {
                field.setAccessible(true);
                if (keysCnt >= 1) {
                    sql.append(" AND");
                }
                sql.append(" ")
                        .append(columnAnnotation.name())
                        .append("=")
                        .append(field.get(record));
            } catch (IllegalAccessException ignored) {}

            keysCnt++;
        }

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int rows = ps.executeUpdate();
            if (rows > 0) {
                log.debug("Deleting successful");
                return true;
            } else {
                log.error("Unable to find a record");
            }
        }
        catch (SQLException e) {
            log.error("Deleting failed", e);
        }

        return false;
    }
}
