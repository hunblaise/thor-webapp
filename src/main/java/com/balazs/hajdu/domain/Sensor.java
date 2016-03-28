package com.balazs.hajdu.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

/**
 * An immutable POJO to store sensor related data.
 *
 * @author Balazs Hajdu
 */
public final class Sensor {

    private final ObjectId id;
    private final String name;
    private final double value;
    private final GeoJsonPoint location;
    private final List<MeasurementResult> measurementResults;

    private Sensor(Builder builder) {
        this.id = builder.id;
        this.name = builder.sensorName;
        this.value = builder.value;
        this.location = builder.location;
        this.measurementResults = builder.measurementResults;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public List<MeasurementResult> getMeasurementResults() {
        return measurementResults;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equal(id, sensor.id) &&
                Objects.equal(name, sensor.name) &&
                Objects.equal(value, sensor.value) &&
                Objects.equal(location, sensor.location) &&
                Objects.equal(measurementResults, sensor.measurementResults);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, value, location, measurementResults);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("value", value)
                .add("location", location)
                .add("measurementResults", measurementResults)
                .toString();
    }

    public static class Builder {

        private ObjectId id;
        private String sensorName;
        private double value;
        private GeoJsonPoint location;
        private List<MeasurementResult> measurementResults;

        public Builder withId(ObjectId id) {
            this.id = id;
            return this;
        }

        public Builder withSensorName(String sensorName) {
            this.sensorName = sensorName;
            return this;
        }

        public Builder withValue(double value) {
            this.value = value;
            return this;
        }

        public Builder withLocation(GeoJsonPoint location) {
            this.location = location;
            return this;
        }

        public Builder withMeasurementResults(List<MeasurementResult> measurementResults) {
            this.measurementResults = measurementResults;
            return this;
        }

        public Sensor build() {
            return new Sensor(this);
        }
    }
    // generated code ends here
}
