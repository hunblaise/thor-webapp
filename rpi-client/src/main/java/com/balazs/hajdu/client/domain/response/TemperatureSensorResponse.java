package com.balazs.hajdu.client.domain.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store temperature sensor measurement result.
 *
 * @author Balazs Hajdu
 */
public final class TemperatureSensorResponse {

    private final MeasurementResult temperature;
    private final MeasurementResult pressure;

    private TemperatureSensorResponse(Builder builder) {
        this.temperature = builder.temperature;
        this.pressure = builder.pressure;
    }

    public MeasurementResult getTemperature() {
        return temperature;
    }

    public MeasurementResult getPressure() {
        return pressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureSensorResponse that = (TemperatureSensorResponse) o;
        return Objects.equal(temperature, that.temperature) &&
                Objects.equal(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(temperature, pressure);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("temperature", temperature)
                .add("pressure", pressure)
                .toString();
    }

    public static class Builder {
        private MeasurementResult temperature;
        private MeasurementResult pressure;

        public Builder withTemperature(MeasurementResult temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder withPressure(MeasurementResult pressure) {
            this.pressure = pressure;
            return this;
        }

        public TemperatureSensorResponse build() {
            return new TemperatureSensorResponse(this);
        }
    }
}
