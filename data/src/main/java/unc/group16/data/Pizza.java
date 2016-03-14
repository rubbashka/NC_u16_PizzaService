package unc.group16.data;

import unc.group16.interfaces.XmlManager;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pizza implements XmlManager {
    private Long id;
    private String name;
    private String type;
    private Integer weight;
    private BigDecimal price;
    private String comments;

    public Pizza() {}

    public Pizza(Long id, String name, String type, Integer weight, BigDecimal price, String comments) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.price = price;
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

    public String getType() {
        return type;
    }

    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    @XmlElement
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @XmlElement
    public void setPrice(BigDecimal price) {
        this.price = price;
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
        return new Pizza(id, name, type, weight, price, comments);
    }
}
