package com.balazs.hajdu.domain.repository.forecast;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDateTime;

/**
 * An immutable POJO to store weather forecast information.
 *
 * @author Balazs Hajdu
 */
public final class ForecastDetail {

    private final String description;
    private final String icon;
    private final int id;
    private final double temperature;
    private final double maxTemperature;
    private final double minTemperature;
    private final double pressure;
    private final double humidity;
    private final LocalDateTime date;

    private ForecastDetail(Builder builder) {
        this.description = builder.description;
        this.icon = builder.icon;
        this.id = builder.id;
        this.temperature = builder.temperature;
        this.maxTemperature = builder.maxTemperature;
        this.minTemperature = builder.minTemperature;
        this.pressure = builder.pressure;
        this.humidity = builder.humidity;
        this.date = builder.date;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
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

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastDetail that = (ForecastDetail) o;
        return id == that.id &&
                Double.compare(that.temperature, temperature) == 0 &&
                Double.compare(that.maxTemperature, maxTemperature) == 0 &&
                Double.compare(that.minTemperature, minTemperature) == 0 &&
                pressure == that.pressure &&
                humidity == that.humidity &&
                Objects.equal(description, that.description) &&
                Objects.equal(icon, that.icon) &&
                Objects.equal(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description, icon, id, temperature, maxTemperature, minTemperature, pressure, humidity, date);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .add("icon", icon)
                .add("id", id)
                .add("temperature", temperature)
                .add("maxTemperature", maxTemperature)
                .add("minTemperature", minTemperature)
                .add("pressure", pressure)
                .add("humidity", humidity)
                .add("date", date)
                .toString();
    }

    public static class Builder {

        private String description;
        private String icon;
        private int id;
        private double temperature;
        private double maxTemperature;
        private double minTemperature;
        private double pressure;
        private double humidity;
        private LocalDateTime date;

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withIcon(String icon) {
            this.icon = icon;
            return this;
        }

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

        public Builder withPressure(double pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder withHumidity(double humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public ForecastDetail build() {
            return new ForecastDetail(this);
        }
    }

}
