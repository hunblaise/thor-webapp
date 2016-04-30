package com.balazs.hajdu.domain.repository.forecast;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDate;

/**
 * An immutable POJO to store daily forecast information.
 *
 * @author Balazs Hajdu
 */
public final class DailyForecast {

    private final int id;
    private final double temperature;
    private final double maxTemperature;
    private final double minTemperature;
    private final LocalDate date;

    private DailyForecast(Builder builder) {
        this.id = builder.id;
        this.temperature = builder.temperature;
        this.maxTemperature = builder.maxTemperature;
        this.minTemperature = builder.minTemperature;
        this.date = builder.date;
    }

    public int getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public LocalDate getDate() {
        return date;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyForecast that = (DailyForecast) o;
        return id == that.id &&
                Double.compare(that.temperature, temperature) == 0 &&
                Double.compare(that.maxTemperature, maxTemperature) == 0 &&
                Double.compare(that.minTemperature, minTemperature) == 0 &&
                Objects.equal(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, temperature, maxTemperature, minTemperature, date);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("temperature", temperature)
                .add("maxTemperature", maxTemperature)
                .add("minTemperature", minTemperature)
                .add("date", date)
                .toString();
    }

    public static class Builder {

        private int id;
        private double temperature;
        private double maxTemperature;
        private double minTemperature;
        private LocalDate date;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder withMaxTemperature(double maxTemperature) {
            this.maxTemperature = maxTemperature;
            return this;
        }

        public Builder withMinTemperature(double minTemperature) {
            this.minTemperature = minTemperature;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public DailyForecast build() {
            return new DailyForecast(this);
        }
    }
    // generated code ends here

}
