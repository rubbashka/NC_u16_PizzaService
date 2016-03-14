package unc.group16.data;

import java.math.BigDecimal;

import com.sun.xml.internal.txw2.annotation.XmlElement;
import unc.group16.annotations.*;
import unc.group16.interfaces.TableRecord;
import unc.group16.interfaces.XmlManager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Table(name = "DRINKS", columns = 5)
public class Drink implements TableRecord, XmlManager {
    @Column(id = 1, name = "DRNK_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "VOLUME")
    private Integer volume;

    @Column(id = 3, name = "PRICE")
    private BigDecimal price;

    @Column(id = 4, name = "NAME")
    private String name;

    @Column(id = 5, name = "COMMENTS")
    private String comments;

    private String test;

    public Drink() {}

    public Drink(Long id, Integer volume, BigDecimal price, String name, String comments) {
        this.id = id;
        this.volume = volume;
        this.price = price;
        this.name = name;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public Drink setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    @XmlElement
    public Drink setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @XmlElement
    public Drink setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public Drink setName(String name) {
        this.name = name;
        return this;
    }

    public String getComments() {
        return comments;
    }

    @XmlElement
    public Drink setComments(String comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public Object clone() {
        return new Drink(id, volume, price, name, comments);
    }
}
