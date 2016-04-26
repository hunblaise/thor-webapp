package com.balazs.hajdu.domain.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store measurement results related data.
 *
 * @author Balazs Hajdu
 */
public class MeasurementResponse {

    private final double value;
    private final String date;

    private MeasurementResponse(Builder builder) {
        this.value = builder.value;
        this.date = builder.date;
    }

    public double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementResponse that = (MeasurementResponse) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equal(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, date);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("date", date)
                .toString();
    }

    public static class Builder {

        private double value;
        private String date;

        public Builder withValue(double value) {
            this.value = value;
            return this;
        }

        public Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public MeasurementResponse build() {
            return new MeasurementResponse(this);
        }

    }

}
