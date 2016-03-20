package com.balazs.hajdu.domain.repository;

import com.balazs.hajdu.domain.AbstractDocument;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

/**
 * Representation of the sensor in the database.
 *
 * @author Balazs Hajdu
 */
public class SensorEntity extends AbstractDocument {

    private String name;
    private Long value;
    private GeoJsonPoint location;
    private List<MeasurementResultEntity> measurementResults;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    public List<MeasurementResultEntity> getMeasurementResults() {
        return measurementResults;
    }

    public void setMeasurementResults(List<MeasurementResultEntity> measurementResults) {
        this.measurementResults = measurementResults;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SensorEntity that = (SensorEntity) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(value, that.value) &&
                Objects.equal(location, that.location) &&
                Objects.equal(measurementResults, that.measurementResults);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, value, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("value", value)
                .add("location", location)
                .add("measurementResults", measurementResults)
                .toString();
    }
    // generated code ends here

}
