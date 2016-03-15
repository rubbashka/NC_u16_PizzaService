package unc.group16.bo.managers.xml;

import org.apache.log4j.Logger;
import unc.group16.data.Drink;
import unc.group16.interfaces.XmlManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Ivan on 13.03.2016.
 */
public class XmlParser {
    public static Long fileCounter = new Long(0);
    public static final Logger log = Logger.getLogger(XmlParser.class);
    public void marshal(XmlManager objectToXML){

        try {

            //Создаем папку для xml-файлов
            File dir = new File("./data/xmlfiles/"
                    + objectToXML.getClass().getSimpleName() + "s/");


            if (!dir.exists()){
                boolean makeDir = dir.mkdir();
                if (makeDir){
                    log.info("Directory successfully created");
                }
                else{
                    log.error("Failed to create directory!");
                }
            }
            //Создаем файл с уникальным именем.
            File file = new File(String.valueOf(dir) + "/"
            + objectToXML.getClass().getSimpleName() + "_"
            + fileCounter++
            + ".xml");

            JAXBContext jaxbContext = JAXBContext.newInstance(Drink.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(objectToXML, file);
            jaxbMarshaller.marshal(objectToXML, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }
}
