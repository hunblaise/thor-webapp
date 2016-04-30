package com.balazs.hajdu.domain.repository.forecast;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store hourly forecast information.
 *
 * @author Balazs Hajdu
 */
public final class HourlyForecast {

    private final String date;
    private final double temperature;

    private HourlyForecast(Builder builder) {
        this.date =builder.date;
        this.temperature = builder.temperature;
    }

    public String getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HourlyForecast that = (HourlyForecast) o;
        return Double.compare(that.temperature, temperature) == 0 &&
                Objects.equal(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date, temperature);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("date", date)
                .add("temperature", temperature)
                .toString();
    }

    public static class Builder {
        private String date;
        private double temperature;

        public Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public Builder withTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public HourlyForecast build() {
            return new HourlyForecast(this);
        }

    }
    // generated code ends here

}
