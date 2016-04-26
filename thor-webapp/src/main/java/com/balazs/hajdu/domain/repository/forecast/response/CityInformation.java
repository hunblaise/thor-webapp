package com.balazs.hajdu.domain.repository.forecast.response;

import com.balazs.hajdu.domain.repository.weather.response.Coordinate;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather forecast information.
 *
 * @author Balazs Hajdu
 */
public class CityInformation {

    private Long id;
    private String name;
    private Coordinate coord;
    private String country;
    private Integer population;

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

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityInformation that = (CityInformation) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(coord, that.coord) &&
                Objects.equal(country, that.country) &&
                Objects.equal(population, that.population);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, coord, country, population);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("coord", coord)
                .add("country", country)
                .add("population", population)
                .toString();
    }

}
