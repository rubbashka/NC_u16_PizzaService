package unc.group16.data;

import unc.group16.interfaces.XmlManager;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ingredient implements XmlManager {
    private Long id;
    private String name;
    private String comments;

    public Ingredient() {}

    public Ingredient(Long id, String name, String comments) {
        this.id = id;
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
        return new Ingredient(id, name, comments);
    }
}
