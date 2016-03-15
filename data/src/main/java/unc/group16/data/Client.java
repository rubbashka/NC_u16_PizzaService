package unc.group16.data;

import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;
import unc.group16.interfaces.XmlManager;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
@Table(name = "CLIENTS", columns = 4)
public class Client implements TableRecord, XmlManager {
    @Column(id = 1, name = "CLNT_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "NAME")
    private String name;

    @Column(id = 3, name = "ADDRESS")
    private String address;

    @Column(id = 4, name = "PHONE_NUMBER")
    private String phone;

    public Client() {}
    
    public Client(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public Client setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    @XmlElement
    public Client setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    @XmlElement
    public Client setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public Object clone() {
        Client result = null;
        try {
            result = (Client) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return result;
    }
}
