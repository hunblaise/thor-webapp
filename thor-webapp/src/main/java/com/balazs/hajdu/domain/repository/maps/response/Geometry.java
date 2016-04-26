package com.balazs.hajdu.domain.repository.maps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store Google Map's geo coding's result.
 *
 * @author Balazs Hajdu
 */
public class Geometry {

    private Location location;
    @JsonProperty(value = "location_type")
    private String locationType;
    private Viewport viewport;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geometry geometry = (Geometry) o;
        return Objects.equal(location, geometry.location) &&
                Objects.equal(locationType, geometry.locationType) &&
                Objects.equal(viewport, geometry.viewport);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location, locationType, viewport);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("location", location)
                .add("locationType", locationType)
                .add("viewport", viewport)
                .toString();
    }
    // generated code ends here

}
