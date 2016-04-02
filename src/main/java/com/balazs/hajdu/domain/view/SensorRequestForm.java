package com.balazs.hajdu.domain.view;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to save sensor into the database.
 *
 * @author Balazs Hajdu
 */
public class SensorRequestForm {

    private String sensorName;
    private double lat;
    private double lon;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

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
        SensorRequestForm that = (SensorRequestForm) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lon, lon) == 0 &&
                Objects.equal(sensorName, that.sensorName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sensorName, lat, lon);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sensorName", sensorName)
                .add("lat", lat)
                .add("lon", lon)
                .toString();
    }
    // generated code ends here

}
