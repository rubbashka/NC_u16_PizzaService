package unc.group16.data;

import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;

import java.math.BigDecimal;


@Table(name = "PIZZAS", columns = 6)
public class Pizza implements TableRecord {
    @Column(id = 1, name = "PZ_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "TITLE")
    private String title;

    @Column(id = 3, name = "TYPE")
    private String type;

    @Column(id = 4, name = "WEIGHT")
    private Integer weight;

    @Column(id = 5, name = "PRICE")
    private BigDecimal price;

    @Column(id = 6, name = "DESCRIPTION")
    private String description;

    public Pizza() {}

    public Pizza(Long id, String title, String type, Integer weight, BigDecimal price, String description) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.weight = weight;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Pizza setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return title;
    }

    public Pizza setName(String title) {
        this.title = title;
        return this;
    }

    public String getType() {
        return type;
    }

    public Pizza setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Pizza setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Pizza setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getComments() {
        return description;
    }

    public Pizza setComments(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Object clone() {
        Pizza result = null;
        try {
            result = (Pizza) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return result;
    }
}
