package com.balazs.hajdu.domain.repository;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representation of the user's location in the database.
 *
 * @author Balazs Hajdu
 */
@Document(collection = "home-control")
public class LocationEntity {

    private String address;
    private double lat;
    private double lon;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        LocationEntity that = (LocationEntity) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lon, lon) == 0 &&
                Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address, lat, lon);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("lat", lat)
                .add("lon", lon)
                .toString();
    }
    // generated code ends here


}
