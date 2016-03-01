package unc.group16.bo;

import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Created by Ivan on 01.03.2016.
 */
public class Console {
    final static Logger logger = Logger.getLogger(Console.class);
    Scanner scanner = new Scanner(System.in);
    String str = "";
    public void start(){
        while (!(str.equals("end"))){
            logger.info("Для выхода из программы введите \"end\"");
            logger.info("Введите имя таблицы. Возможные таблицы:");
            for (Tables tab: Tables.values()){
                System.out.print(tab + " ");
            }
            System.out.println();
            str  = scanner.next();

            logger.info("Введите операцию, которую вы хотите провести над таблицей. Возможные операции:");
            for (Operations op: Operations.values()){
                System.out.print(op + " ");
            }
            System.out.println();
            str = scanner.next();
            break;
        }
    }
}
