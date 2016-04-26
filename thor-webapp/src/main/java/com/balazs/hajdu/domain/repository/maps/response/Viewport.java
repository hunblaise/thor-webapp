package com.balazs.hajdu.domain.repository.maps.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store Google Map's geo coding's result.
 *
 * @author Balazs Hajdu
 */
public class Viewport {

    private Location northeast;
    private Location southwest;

    public Location getNortheast() {
        return northeast;
    }

    public void setNortheast(Location northeast) {
        this.northeast = northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Location southwest) {
        this.southwest = southwest;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viewport viewport = (Viewport) o;
        return Objects.equal(northeast, viewport.northeast) &&
                Objects.equal(southwest, viewport.southwest);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(northeast, southwest);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("northeast", northeast)
                .add("southwest", southwest)
                .toString();
    }
    // generated code ends here

}
