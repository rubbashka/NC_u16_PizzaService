package unc.group16.bo.managers.xml;

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
    public void marshal(XmlManager objectToXML){

        try {

            File file = new File("./data/xmlfiles/"
                    + objectToXML.getClass().getSimpleName() + "_"
                    //Выражения вида
                    //[^@]* - любые символы, кроме @
                    //@ - до первого @, включая @
                    // заменяем на пустую строку
                    + objectToXML.toString().replaceAll("[^@]*@", "")
                    +  ".xml");
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
