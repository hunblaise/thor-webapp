package com.balazs.hajdu.domain.repository.weather;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather information.
 * The object structure based on the openweathermap's JSON contract.
 *
 * @author Balazs Hajdu
 */
public class WeatherInformation {

    Double temp;
    Long pressure;
    Long humidity;
    Double temp_min;
    Double temp_max;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Long getPressure() {
        return pressure;
    }

    public void setPressure(Long pressure) {
        this.pressure = pressure;
    }

    public Long getHumidity() {
        return humidity;
    }

    public void setHumidity(Long humidity) {
        this.humidity = humidity;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherInformation that = (WeatherInformation) o;
        return Objects.equal(temp, that.temp) &&
                Objects.equal(pressure, that.pressure) &&
                Objects.equal(humidity, that.humidity) &&
                Objects.equal(temp_min, that.temp_min) &&
                Objects.equal(temp_max, that.temp_max);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(temp, pressure, humidity, temp_min, temp_max);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("temp", temp)
                .add("pressure", pressure)
                .add("humidity", humidity)
                .add("temp_min", temp_min)
                .add("temp_max", temp_max)
                .toString();
    }

}
