package org.netcracker.unc.group16.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SaucesCRUD {

    //Create
    //Без первичного ключа
    public void insertIntoSauces(String def, String comments){
        JDBC jdbc = new JDBC();
        Connection conn = jdbc.setConnection();


        String sql = "INSERT INTO SAUCES (def, comments) VALUES (?, ?)";


        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, def);
            statement.setString(2, comments);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inserted into SAUCES successfully.");
            }
        }
        catch (java.sql.SQLException e){
            System.out.println("Inserting into failed.");
            e.printStackTrace();
        }
        try {
            conn.close();
        }
        catch (java.sql.SQLException e){
            System.out.println("Closing connection failed.");
            e.printStackTrace();
        }
    }

    //Create
    public void insertIntoSauces(int sc_id, String def, String comments){
        JDBC jdbc = new JDBC();
        Connection conn = jdbc.setConnection();


        String sql = "INSERT INTO SAUCES (sc_id, def, comments) VALUES (?, ?, ?)";


        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setNull(1, java.sql.Types.VARCHAR);
            statement.setString(2, "Проверка");
            String thirdParameter = "Алло";
            InputStream stream = new ByteArrayInputStream(thirdParameter.getBytes(StandardCharsets.US_ASCII));
            statement.setAsciiStream(3, stream);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inserted into SAUCES successfully.");
            }
        }
        catch (java.sql.SQLException e){
            System.out.println("Inserting into failed.");
            e.printStackTrace();
        }
        try {
            conn.close();
        }
        catch (java.sql.SQLException e){
            System.out.println("Closing connection failed.");
            e.printStackTrace();
        }
    }

}
