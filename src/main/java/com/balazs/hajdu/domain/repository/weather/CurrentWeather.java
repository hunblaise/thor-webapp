package com.balazs.hajdu.domain.repository.weather;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * A POJO to store weather information.
 * The structure based on the openweathermap's JSON contract.
 * http://openweathermap.org/current
 *
 * @author Balazs Hajdu
 */
public class CurrentWeather {

    Coordinate coord;
    List<WeatherType> weather;
    String base;
    WeatherInformation main;
    Wind wind;
    Cloud clouds;
    Rain rain;
    Snow snow;
    Long dt;
    AdditionalWeatherInformation sys;
    Long id;
    String name;
    Long cod;

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public List<WeatherType> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherType> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public WeatherInformation getMain() {
        return main;
    }

    public void setMain(WeatherInformation main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public AdditionalWeatherInformation getSys() {
        return sys;
    }

    public void setSys(AdditionalWeatherInformation sys) {
        this.sys = sys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentWeather that = (CurrentWeather) o;
        return Objects.equal(coord, that.coord) &&
                Objects.equal(weather, that.weather) &&
                Objects.equal(base, that.base) &&
                Objects.equal(main, that.main) &&
                Objects.equal(wind, that.wind) &&
                Objects.equal(clouds, that.clouds) &&
                Objects.equal(rain, that.rain) &&
                Objects.equal(snow, that.snow) &&
                Objects.equal(dt, that.dt) &&
                Objects.equal(sys, that.sys) &&
                Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(cod, that.cod);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coord, weather, base, main, wind, clouds, rain, snow, dt, sys, id, name, cod);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("coord", coord)
                .add("weather", weather)
                .add("base", base)
                .add("main", main)
                .add("wind", wind)
                .add("clouds", clouds)
                .add("rain", rain)
                .add("snow", snow)
                .add("dt", dt)
                .add("sys", sys)
                .add("id", id)
                .add("name", name)
                .add("cod", cod)
                .toString();
    }
}
