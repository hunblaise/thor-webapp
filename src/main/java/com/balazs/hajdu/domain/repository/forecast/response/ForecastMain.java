package com.balazs.hajdu.domain.repository.forecast.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather forecast information.
 *
 * @author Balazs Hajdu
 */
public class ForecastMain {

    private Double temp;
    @JsonProperty("temp_min")
    private Double tempMin;
    @JsonProperty("temp_max")
    private Double tempMax;
    private Double pressure;
    @JsonProperty("sea_level")
    private Double seaLevel;
    @JsonProperty("grnd_level")
    private Double grndLevel;
    private Double humidity;
    @JsonProperty("temp_kf")
    private Double internalParameter;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
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

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Double seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Double grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getInternalParameter() {
        return internalParameter;
    }

    public void setInternalParameter(Double internalParameter) {
        this.internalParameter = internalParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastMain that = (ForecastMain) o;
        return Objects.equal(temp, that.temp) &&
                Objects.equal(tempMin, that.tempMin) &&
                Objects.equal(tempMax, that.tempMax) &&
                Objects.equal(pressure, that.pressure) &&
                Objects.equal(seaLevel, that.seaLevel) &&
                Objects.equal(grndLevel, that.grndLevel) &&
                Objects.equal(humidity, that.humidity) &&
                Objects.equal(internalParameter, that.internalParameter);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(temp, tempMin, tempMax, pressure, seaLevel, grndLevel, humidity, internalParameter);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("temp", temp)
                .add("tempMin", tempMin)
                .add("tempMax", tempMax)
                .add("pressure", pressure)
                .add("seaLevel", seaLevel)
                .add("grndLevel", grndLevel)
                .add("humidity", humidity)
                .add("internalParameter", internalParameter)
                .toString();
    }
}
