package com.balazs.hajdu.domain.repository;

import com.balazs.hajdu.domain.AbstractDocument;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Representation of sensors' measurement results.
 *
 * @author Balazs Hajdu
 */
@Document(collection = "measurement-results")
public class MeasurementResultEntity extends AbstractDocument {

    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDateTime date;
    private double value;
    private String username;
    private String sensorName;
    private GeoJsonPoint location;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MeasurementResultEntity that = (MeasurementResultEntity) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equal(date, that.date) &&
                Objects.equal(username, that.username) &&
                Objects.equal(sensorName, that.sensorName) &&
                Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), date, value, username, sensorName, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("date", date)
                .add("value", value)
                .add("username", username)
                .add("sensorName", sensorName)
                .add("location", location)
                .toString();
    }
    // generated code ends here

}
