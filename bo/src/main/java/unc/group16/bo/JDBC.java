package unc.group16.bo;

import org.apache.log4j.Logger;
import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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
        Long result = null;

        try (Connection connection = getConnection()) {
            result = insert(record, connection);
        } catch (SQLException e) {
            log.error("Inserting failed", e);
        }

        return result;
    }

    public Long insert(TableRecord record, Connection con) {
        Table tableInfo = record.getClass().getDeclaredAnnotation(Table.class);

        String sql = "INSERT INTO " + tableInfo.name() + " VALUES (null" + new String(new char[tableInfo.columns()-1]).replace("\0", ", ?") + ")";

        try ( PreparedStatement ps = con.prepareStatement(sql, new int[] {1}) ) {
            Field[] fields = record.getClass().getDeclaredFields();
            for (Field field : fields) {
                Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
                if (columnAnnotation != null && !columnAnnotation.isKey()) {
                    try {
                        field.setAccessible(true);
                        Object data = field.get(record);
                        if (data instanceof Date) {
                            data = new Timestamp(((Date) data).getTime());
                        }
                        ps.setObject(columnAnnotation.id() - 1, data);
                    } catch (IllegalAccessException e) {
                        log.error("Could not get access to field " + field.getName(), e);
                    }
                }
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

        return null;
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
        Table tableInfo = record.getClass().getDeclaredAnnotation(Table.class);

        StringBuilder sql = new StringBuilder("SELECT * FROM ")
                .append(tableInfo.name())
                .append(" WHERE ");

        Field[] fields = record.getClass().getDeclaredFields();
        for (Field field : fields) {
            Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
            if (columnAnnotation != null && columnAnnotation.isKey()) {
                try {
                    field.setAccessible(true);
                    sql.append(columnAnnotation.name())
                            .append("=")
                            .append(field.get(record));
                } catch (IllegalAccessException e) {
                    log.error("Could not get access to field " + field.getName(), e);
                }
                break;
            }
        }

        try ( PreparedStatement ps = con.prepareStatement(sql.toString());
              ResultSet rs = ps.executeQuery() ) {
            TableRecord result = record.getClass().newInstance();
            if (rs == null || !rs.next()) {
                log.error("Unable to find a record");
                return null;
            }

            for (Field field : fields) {
                Column columnAnnotation = field.getDeclaredAnnotation(Column.class);

                if (columnAnnotation != null) {
                    field.setAccessible(true);

                    Object data;
                    if (field.getType() == Long.class) {
                        data = rs.getLong(columnAnnotation.id());
                    } else if (field.getType() == Integer.class) {
                        data = rs.getInt(columnAnnotation.id());
                    } else if (field.getType() == Double.class) {
                        data = rs.getDouble(columnAnnotation.id());
                    } else if (field.getType() == Date.class) {
                        data = new Date(rs.getTimestamp(columnAnnotation.id()).getTime());
                    } else {
                        data = rs.getObject(columnAnnotation.id());
                    }
                    field.set(result, data);
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

    public TableRecord[] selectAll(Class<? extends TableRecord> table) {
        TableRecord[] result = null;

        try (Connection connection = getConnection()) {
            result = selectAll(table, connection);
        } catch (SQLException e) {
            log.error("Selecting failed", e);
        }

        return result;
    }

    public TableRecord[] selectAll(Class<? extends TableRecord> table, Connection con) {
        Table tableInfo = table.getDeclaredAnnotation(Table.class);

        String sql = "SELECT * FROM " + tableInfo.name();

        ArrayList<TableRecord> result = new ArrayList<>();

        try ( PreparedStatement ps = con.prepareStatement(sql);
              ResultSet rs = ps.executeQuery() ) {
            if (rs == null) {
                log.error("Unable to find records");
                return null;
            }

            Field[] fields = table.getDeclaredFields();
            while (rs.next()) {
                TableRecord record = table.newInstance();

                for (Field field : fields) {
                    Column columnAnnotation = field.getDeclaredAnnotation(Column.class);

                    if (columnAnnotation != null) {
                        field.setAccessible(true);

                        Object data;
                        if (field.getType() == Long.class) {
                            data = rs.getLong(columnAnnotation.id());
                        } else if (field.getType() == Integer.class) {
                            data = rs.getInt(columnAnnotation.id());
                        } else if (field.getType() == Double.class) {
                            data = rs.getDouble(columnAnnotation.id());
                        } else if (field.getType() == Date.class) {
                            data = new Date(rs.getTimestamp(columnAnnotation.id()).getTime());
                        } else {
                            data = rs.getObject(columnAnnotation.id());
                        }
                        field.set(record, data);
                    }
                }

                result.add(record);
            }

            log.debug("Selected successfully");
            return result.toArray(new TableRecord[result.size()]);

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
        Table tableInfo = record.getClass().getDeclaredAnnotation(Table.class);

        StringBuilder sql = new StringBuilder("UPDATE ")
                .append(tableInfo.name())
                .append(" SET");
        StringBuilder whereStatement = new StringBuilder(" WHERE ");

        Field[] fields = record.getClass().getDeclaredFields();
        int colCnt = 0;
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
                    if (field.get(record) == null) {
                        log.error("Key value must be not null");
                        return false;
                    }
                    whereStatement.append(columnAnnotation.name())
                            .append("=")
                            .append(field.get(record));
                }

            } catch (IllegalAccessException e) {
                log.error("Could not get access to field " + field.getName(), e);
            }
        }
        if (colCnt == 0) {
            log.error("At least one non key argument must be not null");
            return false;
        }
        sql.append(whereStatement);

        try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int i = 0;
            for (Field field : fields) {
                Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
                if (columnAnnotation != null && !columnAnnotation.isKey() && field.get(record) != null) {
                    Object data = field.get(record);
                    if (data instanceof Date) {
                        data = new Timestamp(((Date) data).getTime());
                    }
                    ps.setObject(++i, data);
                }
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
        Table tableInfo = record.getClass().getDeclaredAnnotation(Table.class);

        StringBuilder sql = new StringBuilder("DELETE FROM ")
                .append(tableInfo.name())
                .append(" WHERE ");
        Field[] fields = record.getClass().getDeclaredFields();
        for (Field field : fields) {
            Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
            if (columnAnnotation != null && columnAnnotation.isKey()) {
                try {
                    field.setAccessible(true);
                    sql.append(columnAnnotation.name())
                            .append("=")
                            .append(field.get(record));
                } catch (IllegalAccessException e) {
                    log.error("Could not get access to field " + field.getName(), e);
                }
                break;
            }
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
