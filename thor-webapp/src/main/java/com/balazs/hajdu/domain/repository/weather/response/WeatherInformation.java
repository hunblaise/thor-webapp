package com.balazs.hajdu.domain.repository.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "temp_min")
    Double tempMin;
    @JsonProperty(value = "tempMax")
    Double tempMax;

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

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherInformation that = (WeatherInformation) o;
        return Objects.equal(temp, that.temp) &&
                Objects.equal(pressure, that.pressure) &&
                Objects.equal(humidity, that.humidity) &&
                Objects.equal(tempMin, that.tempMin) &&
                Objects.equal(tempMax, that.tempMax);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(temp, pressure, humidity, tempMin, tempMax);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("temp", temp)
                .add("pressure", pressure)
                .add("humidity", humidity)
                .add("tempMin", tempMin)
                .add("tempMax", tempMax)
                .toString();
    }

}
