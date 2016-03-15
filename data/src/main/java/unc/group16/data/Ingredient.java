package unc.group16.data;

import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;
import unc.group16.interfaces.XmlManager;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;


@XmlRootElement
@Table(name = "INGREDIENTS", columns = 3)
public class Ingredient implements TableRecord, XmlManager {
    @Column(id = 1, name = "INGRD_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "TITLE")
    private String title;

    @Column(id = 3, name = "DESCRIPTION")
    private String description;

    public Ingredient() {}

    public Ingredient(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public Ingredient setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return title;
    }

    @XmlElement
    public Ingredient setName(String title) {
        this.title = title;
        return this;
    }

    public String getComments() {
        return description;
    }

    @XmlElement
    public Ingredient setComments(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Object clone() {
        Ingredient result = null;
        try {
            result = (Ingredient) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return result;
    }
}
