package unc.group16.data;

import unc.group16.interfaces.XmlManager;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MeasurementUnit implements XmlManager {
    private Long id;
    private String name;

    public MeasurementUnit() {}

    public MeasurementUnit(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public Object clone() {
        return new MeasurementUnit(id, name);
    }
}
