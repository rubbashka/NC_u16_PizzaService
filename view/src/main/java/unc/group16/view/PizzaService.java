package unc.group16.view;


import org.apache.log4j.Logger;
import unc.group16.bo.managers.xml.XmlParser;
import unc.group16.data.Drink;

import java.math.BigDecimal;


public class PizzaService {
    final static Logger logger = Logger.getLogger(PizzaService.class);
    PizzaService pizzaService = new PizzaService();
    public PizzaService(){

    }

    public static void main (String args[]){
        Drink drink = new Drink(new Long(5), 300, new BigDecimal(500), "Тестовый напиток", "Тестовый коммент");

        XmlParser drinkToXML = new XmlParser();
        drinkToXML.marshal(drink);

    }

}
