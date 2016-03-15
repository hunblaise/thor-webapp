package com.balazs.hajdu.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author Balazs Hajdu
 */
public class Sensor {

    private final String sensorName;

    private Sensor(Builder builder) {
        this.sensorName = builder.sensorName;
    }

    public String getSensorName() {
        return sensorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equal(sensorName, sensor.sensorName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sensorName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sensorName", sensorName)
                .toString();
    }

    public static class Builder {

        private String sensorName;

        public Builder withSensorName(String sensorName) {
            this.sensorName = sensorName;
            return this;
        }

        public Sensor build() {
            return new Sensor(this);
        }
    }
}
