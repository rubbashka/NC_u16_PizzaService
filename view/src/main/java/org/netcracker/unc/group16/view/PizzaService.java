package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.data.Sauces;

/**
 * Created by Ivan on 18.02.2016.
 */
public class PizzaService {
    public static void main (String args[]){
      //  Pizzas pizzasCRUD = new Pizzas();
       // pizzasCRUD.insertIntoPizzas();

        Sauces saucesCRUD = new Sauces();
        saucesCRUD.insertIntoSauces("Тестовый соус", "С очень вкусным описанием");
    }
}
