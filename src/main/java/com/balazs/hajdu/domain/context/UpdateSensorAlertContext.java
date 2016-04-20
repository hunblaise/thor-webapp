package com.balazs.hajdu.domain.context;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to encapsulate sensor alert value update context.
 *
 * @author Balazs Hajdu
 */
public final class UpdateSensorAlertContext {

    private final String username;
    private final String sensorName;
    private final double maxValue;
    private final double minValue;

    private UpdateSensorAlertContext(Builder builder) {
        this.username = builder.username;
        this.sensorName = builder.sensorName;
        this.maxValue = builder.maxValue;
        this.minValue = builder.minValue;
    }

    public String getUsername() {
        return username;
    }

    public String getSensorName() {
        return sensorName;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateSensorAlertContext that = (UpdateSensorAlertContext) o;
        return Double.compare(that.maxValue, maxValue) == 0 &&
                Double.compare(that.minValue, minValue) == 0 &&
                Objects.equal(username, that.username) &&
                Objects.equal(sensorName, that.sensorName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, sensorName, maxValue, minValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("sensorName", sensorName)
                .add("maxValue", maxValue)
                .add("minValue", minValue)
                .toString();
    }

    public static class Builder {

        private String username;
        private String sensorName;
        private double maxValue;
        private double minValue;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withSensorName(String sensorName) {
            this.sensorName = sensorName;
            return this;
        }

        public Builder withMaxValue(double maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public Builder withMinValue(double minValue) {
            this.minValue = minValue;
            return this;
        }

        public UpdateSensorAlertContext build() {
            return new UpdateSensorAlertContext(this);
        }

    }

}
