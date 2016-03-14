package unc.group16.data;

import unc.group16.interfaces.XmlManager;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Sauce implements XmlManager {
    private Long id;
    private BigDecimal price;
    private String name;
    private String comments;

    public Sauce() {}

    public Sauce(Long id, BigDecimal price, String name, String comments) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
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
        return new Sauce(id, price, name, comments);
    }
}
