package unc.group16.data;

public class MeasurementUnit {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() {
        return new MeasurementUnit(id, name);
    }
}
