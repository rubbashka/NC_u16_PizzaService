package unc.group16.data;

import java.math.BigDecimal;


public class Drink {
    private Long id;
    private Integer volume;
    private BigDecimal price;
    private String name;
    private String comments;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public Object clone() {
        return new Drink(id, volume, price, name, comments);
    }
}
