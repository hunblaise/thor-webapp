package com.balazs.hajdu.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;

/**
 * An immutable POJO to store measurement results.
 *
 * @author Balazs Hajdu
 */
public final class MeasurementResult {

    private final ObjectId id;
    private final double value;
    private final LocalDateTime date;
    private final GeoJsonPoint location;
    private final String username;
    private final String sensorName;

    private MeasurementResult(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
        this.value = builder.value;
        this.location = builder.location;
        this.username = builder.username;
        this.sensorName = builder.sensorName;
    }

    public ObjectId getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public String getUsername() {
        return username;
    }

    public String getSensorName() {
        return sensorName;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementResult that = (MeasurementResult) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equal(id, that.id) &&
                Objects.equal(date, that.date) &&
                Objects.equal(location, that.location) &&
                Objects.equal(username, that.username) &&
                Objects.equal(sensorName, that.sensorName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, value, date, location, username, sensorName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("value", value)
                .add("date", date)
                .add("location", location)
                .add("username", username)
                .add("sensorName", sensorName)
                .toString();
    }

    public static class Builder {

        private ObjectId id;
        private double value;
        private LocalDateTime date;
        private GeoJsonPoint location;
        private String username;
        private String sensorName;

        public Builder withId(ObjectId id) {
            this.id = id;
            return this;
        }

        public Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withValue(double value) {
            this.value = value;
            return this;
        }

        public Builder withLocation(double latitude, double longitude) {
            this.location = new GeoJsonPoint(latitude, longitude);
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withSensorName(String sensorName) {
            this.sensorName = sensorName;
            return this;
        }

        public MeasurementResult build() {
            return new MeasurementResult(this);
        }

    }
    // generated code ends here

}
