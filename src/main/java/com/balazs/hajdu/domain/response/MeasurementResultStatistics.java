package com.balazs.hajdu.domain.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store measurement result statistics.
 *
 * @author Balazs Hajdu
 */
public class MeasurementResultStatistics {

    private final double average;
    private final double max;
    private final double min;

    private MeasurementResultStatistics(Builder builder) {
        this.average = builder.average;
        this.max = builder.max;
        this.min = builder.min;
    }

    public double getAverage() {
        return average;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementResultStatistics that = (MeasurementResultStatistics) o;
        return Double.compare(that.average, average) == 0 &&
                Double.compare(that.max, max) == 0 &&
                Double.compare(that.min, min) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(average, max, min);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("average", average)
                .add("max", max)
                .add("min", min)
                .toString();
    }

    public static class Builder {

        private double average;
        private double max;
        private double min;

        public Builder withAverage(double average) {
            this.average = average;
            return this;
        }

        public Builder withMax(double max) {
            this.max = max;
            return this;
        }

        public Builder withMin(double min) {
            this.min = min;
            return this;
        }

        public MeasurementResultStatistics build() {
            return new MeasurementResultStatistics(this);
        }

    }
    // generated code ends here

}
