package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.data.JDBC;
import org.netcracker.unc.group16.data.PizzasCRUD;
import org.netcracker.unc.group16.data.SauceCRUD;

/**
 * Created by Ivan on 18.02.2016.
 */
public class PizzaService {
    public static void main (String args[]){
      //  PizzasCRUD pizzasCRUD = new PizzasCRUD();
       // pizzasCRUD.insertIntoPizzas();

        SauceCRUD sauceCRUD = new SauceCRUD();
        sauceCRUD.insertIntoSauce();
    }
}
