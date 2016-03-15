package unc.group16.data;

import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;
import unc.group16.interfaces.XmlManager;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
@Table(name = "SAUCES", columns = 4)
public class Sauce implements TableRecord, XmlManager {
    @Column(id = 1, name = "SC_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "PRICE")
    private BigDecimal price;

    @Column(id = 3, name = "TITLE")
    private String title;

    @Column(id = 4, name = "DESCRIPTION")
    private String description;

    public Sauce() {}

    public Sauce(Long id, BigDecimal price, String title, String description) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public Sauce setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @XmlElement
    public Sauce setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getName() {
        return title;
    }

    @XmlElement
    public Sauce setName(String title) {
        this.title = title;
        return this;
    }

    public String getComments() {
        return description;
    }

    @XmlElement
    public Sauce setComments(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Object clone() {
        Sauce result = null;
        try {
            result = (Sauce) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return result;
    }
}
