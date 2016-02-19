package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.data.SaucesCRUD;

/**
 * Created by Ivan on 18.02.2016.
 */
public class PizzaService {
    public static void main (String args[]){
      //  PizzasCRUD pizzasCRUD = new PizzasCRUD();
       // pizzasCRUD.insertIntoPizzas();

        SaucesCRUD saucesCRUD = new SaucesCRUD();
        saucesCRUD.insertIntoSauces("Тестовый соус", "С очень вкусным описанием");
    }
}
