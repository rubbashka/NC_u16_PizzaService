package unc.group16.data;

import unc.group16.annotations.Column;
import unc.group16.annotations.Table;
import unc.group16.interfaces.TableRecord;


@Table(name = "MEASUREMENT_UNITS", columns = 2)
public class MeasurementUnit implements TableRecord {
    @Column(id = 1, name = "MSRU_ID", isKey = true)
    private Long id;

    @Column(id = 2, name = "TITLE")
    private String title;

    public MeasurementUnit() {}

    public MeasurementUnit(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public MeasurementUnit setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return title;
    }

    public MeasurementUnit setName(String title) {
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
