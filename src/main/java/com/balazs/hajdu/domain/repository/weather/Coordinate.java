package com.balazs.hajdu.domain.repository.weather;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather information.
 * The object structure based on the openweathermap's JSON contract.
 *
 * @author Balazs Hajdu
 */
public class Coordinate {

    Double lon;
    Double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equal(lon, that.lon) &&
                Objects.equal(lat, that.lat);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lon, lat);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lon", lon)
                .add("lat", lat)
                .toString();
    }

}
