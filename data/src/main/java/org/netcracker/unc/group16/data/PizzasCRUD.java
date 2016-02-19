package org.netcracker.unc.group16.data;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class PizzasCRUD {

    //Create
    public void insertIntoPizzas(){
        JDBC jdbc = new JDBC();
        Connection conn = jdbc.setConnection();


        String sql = "INSERT INTO PIZZAS (pz_id, def, comments) VALUES (?, ?, ?)";

        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "1");
            statement.setString(2, "Mocarella");
            statement.setString(3, "Классическая итальянская пицца");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        }
        catch (java.sql.SQLException e){
            System.out.println("Inserting failed");
            e.printStackTrace();
        }
        try {
            conn.close();
        }
        catch (java.sql.SQLException e){
            System.out.println("Closing connection failed");
            e.printStackTrace();
        }
    }
}
