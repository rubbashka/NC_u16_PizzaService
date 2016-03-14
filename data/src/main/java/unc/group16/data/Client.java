package unc.group16.data;

import javax.xml.bind.annotation.XmlElement;
import unc.group16.interfaces.XmlManager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Client implements XmlManager {
    private Long id;
    private String name;
    private String address;
    private String home;
    private String apartment;
    private String phone;
    private String comments;

    public Client() {}
    
    public Client(Long id, String name, String address, String home, String apartment, String phone, String comments) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.home = home;
        this.apartment = apartment;
        this.phone = phone;
        this.comments = comments;
    }
    
    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    @XmlElement
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getHome() {
        return home;
    }

    @XmlElement
    public void setHome(String home) {
        this.home = home;
    }
    
    public String getApartment() {
        return apartment;
    }

    @XmlElement
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPhone() {
        return phone;
    }

    @XmlElement
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComments() {
        return comments;
    }

    @XmlElement
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public Object clone() {
        return new Client(id, name, address, home, apartment, phone, comments);
    }
}
