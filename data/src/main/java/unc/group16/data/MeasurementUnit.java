package unc.group16.data;

import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;
import unc.group16.interfaces.XmlManager;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;


@XmlRootElement
@Table(name = "MEASUREMENT_UNITS", columns = 2)
public class MeasurementUnit implements TableRecord, XmlManager {
    @Column(id = 1, name = "MSRU_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "TITLE")
    private String title;

    public MeasurementUnit() {}

    public MeasurementUnit(String title) {
        this.title = title;
    }

    public MeasurementUnit(Long id, String title) {
        this(title);
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    @XmlElement
    public MeasurementUnit setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public MeasurementUnit setTitle(String title) {
        this.title = title;
        return this;
    }


    @Override
    public Object clone() {
        MeasurementUnit result = null;
        try {
            result = (MeasurementUnit) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return result;
    }
}
