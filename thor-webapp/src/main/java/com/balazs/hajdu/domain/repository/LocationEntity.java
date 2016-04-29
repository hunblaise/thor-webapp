package com.balazs.hajdu.domain.repository;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representation of the user's location in the database.
 *
 * @author Balazs Hajdu
 */
@Document(collection = "users")
public class LocationEntity {

    private String address;
    private GeoJsonPoint location;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return Objects.equal(address, that.address) &&
                Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("address", address)
                .add("location", location)
                .toString();
    }
    // generated code ends here


}
