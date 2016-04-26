package com.balazs.hajdu.domain.repository.maps;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store geocoded location.
 *
 * @author Balazs Hajdu
 */
public final class GeocodedLocation {

    private final String formattedLocation;
    private final Location location;

    private GeocodedLocation(Builder builder) {
        this.formattedLocation = builder.formattedLocation;
        this.location = builder.location;
    }

    public String getFormattedLocation() {
        return formattedLocation;
    }

    public Location getLocation() {
        return location;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeocodedLocation that = (GeocodedLocation) o;
        return Objects.equal(formattedLocation, that.formattedLocation) &&
                Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(formattedLocation, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("formattedLocation", formattedLocation)
                .add("location", location)
                .toString();
    }

    public static class Builder {

        private String formattedLocation;
        private Location location;

        public Builder withFormattedLocation(String formattedLocation) {
            this.formattedLocation = formattedLocation;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public GeocodedLocation build() {
            return new GeocodedLocation(this);
        }

    }
    // generated code ends here

}
