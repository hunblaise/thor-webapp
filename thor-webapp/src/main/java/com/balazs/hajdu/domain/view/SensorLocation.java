package com.balazs.hajdu.domain.view;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store the sensor location.
 *
 * @author Balazs Hajdu
 */
public class SensorLocation {

    double lat;
    double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorLocation that = (SensorLocation) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lon, lon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lat, lon);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("lat", lat)
                .add("lon", lon)
                .toString();
    }
    // generated code ends here

}
