package com.balazs.hajdu.domain.repository.forecast.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Balazs Hajdu
 */
public class ForecastInformation {

    private Long dt;
    private ForecastMain main;
    private List<ForecastWeather> weather;
    private ForecastClouds clouds;
    private ForecastWind wind;
    @JsonProperty("dt_txt")
    private LocalDateTime date;

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public ForecastMain getMain() {
        return main;
    }

    public void setMain(ForecastMain main) {
        this.main = main;
    }

    public List<ForecastWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<ForecastWeather> weather) {
        this.weather = weather;
    }

    public ForecastClouds getClouds() {
        return clouds;
    }

    public void setClouds(ForecastClouds clouds) {
        this.clouds = clouds;
    }

    public ForecastWind getWind() {
        return wind;
    }

    public void setWind(ForecastWind wind) {
        this.wind = wind;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastInformation that = (ForecastInformation) o;
        return Objects.equal(dt, that.dt) &&
                Objects.equal(main, that.main) &&
                Objects.equal(weather, that.weather) &&
                Objects.equal(clouds, that.clouds) &&
                Objects.equal(wind, that.wind) &&
                Objects.equal(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dt, main, weather, clouds, wind, date);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dt", dt)
                .add("main", main)
                .add("weather", weather)
                .add("clouds", clouds)
                .add("wind", wind)
                .add("date", date)
                .toString();
    }
}
