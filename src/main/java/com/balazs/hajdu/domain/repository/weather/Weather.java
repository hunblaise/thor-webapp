package com.balazs.hajdu.domain.repository.weather;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store weather information.
 *
 * @author Balazs Hajdu
 */
public final class Weather {

    private final String description;
    private final String icon;
    private final String iconName;
    private final double temperature;
    private final long pressure;
    private final long humidity;

    private Weather(Builder builder) {
        this.description = builder.description;
        this.icon = builder.icon;
        this.iconName = builder.iconName;
        this.temperature = builder.temperature;
        this.pressure = builder.pressure;
        this.humidity = builder.humidity;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconName() {
        return iconName;
    }

    public double getTemperature() {
        return temperature;
    }

    public long getPressure() {
        return pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Double.compare(weather.temperature, temperature) == 0 &&
                pressure == weather.pressure &&
                humidity == weather.humidity &&
                Objects.equal(description, weather.description) &&
                Objects.equal(icon, weather.icon) &&
                Objects.equal(iconName, weather.iconName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description, icon, iconName, temperature, pressure, humidity);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .add("icon", icon)
                .add("iconName", iconName)
                .add("temperature", temperature)
                .add("pressure", pressure)
                .add("humidity", humidity)
                .toString();
    }

    public static class Builder {

        private String description;
        private String icon;
        private String iconName;
        private double temperature;
        private long pressure;
        private long humidity;

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder withIconName(String iconName) {
            this.iconName = iconName;
            return this;
        }

        public Builder withTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder withPressure(long pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder withHumidity(long humidity) {
            this.humidity = humidity;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }

    }
    // generated code ends here

}
