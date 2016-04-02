package com.balazs.hajdu.domain.repository.weather.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather information.
 * The object structure based on the openweathermap's JSON contract.
 *
 * @author Balazs Hajdu
 */
public class AdditionalWeatherInformation {

    Integer type;
    Long id;
    String message;
    String country;
    Long sunrise;
    Long sunset;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdditionalWeatherInformation that = (AdditionalWeatherInformation) o;
        return Objects.equal(type, that.type) &&
                Objects.equal(id, that.id) &&
                Objects.equal(message, that.message) &&
                Objects.equal(country, that.country) &&
                Objects.equal(sunrise, that.sunrise) &&
                Objects.equal(sunset, that.sunset);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, id, message, country, sunrise, sunset);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("id", id)
                .add("message", message)
                .add("country", country)
                .add("sunrise", sunrise)
                .add("sunset", sunset)
                .toString();
    }

}
