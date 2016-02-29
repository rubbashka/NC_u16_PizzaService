package unc.group16.data;

import java.math.BigDecimal;


public class Pizza {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public Object clone() {
        return new Pizza(id, name, type, weight, price, comments);
    }
}
