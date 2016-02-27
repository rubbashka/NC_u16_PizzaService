package unc.group16.bo.managers;

import unc.group16.bo.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class PizzasManager {

    //Create
    public void insertIntoPizzas(){
        JDBC jdbc = new JDBC();
        Connection conn = jdbc.setConnection();


        String sql = "INSERT INTO PIZZAS (pz_id, def, comments) VALUES (?, ?, ?)";

        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setNull(1, java.sql.Types.VARCHAR);
            statement.setString(2, "Mocarella");
            statement.setString(3, "Classical Itallian pizza");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Inserted into PIZZAS successfully.");
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
