package com.balazs.hajdu.client.domain.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store measurement result.
 *
 * @author Balazs Hajdu
 */
public final class MeasurementResult {

    private final double value;
    private final String error;

    private MeasurementResult(Builder builder) {
        this.value = builder.value;
        this.error = builder.error;
    }

    public double getValue() {
        return value;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementResult that = (MeasurementResult) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equal(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, error);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("error", error)
                .toString();
    }

    public static class Builder {
        private double value;
        private String error;

        public Builder withValue(double value) {
            this.value = value;
            return this;
        }

        public Builder withError(String error) {
            this.error = error;
            return this;
        }

        public MeasurementResult build() {
            return new MeasurementResult(this);
        }

    }
}
