package org.netcracker.unc.group16.data;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SauceCRUD {

    //Create
    public void insertIntoSauce(){
        JDBC jdbc = new JDBC();
        Connection conn = jdbc.setConnection();


        String sql = "INSERT INTO SAUCES (sc_id, def, comments) VALUES (?, ?, ?)";

        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setNull(1, java.sql.Types.VARCHAR);
            statement.setString(2, "Томаты");
            statement.setNull(3, java.sql.Types.VARCHAR);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inserted into SAUCES successfully.");
            }
        }
        catch (java.sql.SQLException e){
            System.out.println("Inserting failed.");
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
