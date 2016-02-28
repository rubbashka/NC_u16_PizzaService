package unc.group16.view;

import unc.group16.data.*;
import unc.group16.data.Drink;
import unc.group16.bo.managers.DrinksManager;
import org.apache.log4j.Logger;



public class PizzaService {
    final static Logger logger = Logger.getLogger(PizzaService.class);
    PizzaService pizzaService = new PizzaService();
    public PizzaService(){

    }

    public static void main (String args[]){
        if(logger.isDebugEnabled()){
            logger.debug("This is debug");
        }

        //logs an error message with parameter
        logger.error("This is error : ");

        //logs an exception thrown from somewhere
        logger.error("This is error");
        DrinksManager drinks = new DrinksManager();
    }

}
