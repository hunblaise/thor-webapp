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
    private final Coordinates coordinates;

    private GeocodedLocation(Builder builder) {
        this.formattedLocation = builder.formattedLocation;
        this.coordinates = builder.coordinates;
    }

    public String getFormattedLocation() {
        return formattedLocation;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeocodedLocation that = (GeocodedLocation) o;
        return Objects.equal(formattedLocation, that.formattedLocation) &&
                Objects.equal(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(formattedLocation, coordinates);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("formattedLocation", formattedLocation)
                .add("location", coordinates)
                .toString();
    }

    public static class Builder {

        private String formattedLocation;
        private Coordinates coordinates;

        public Builder withFormattedLocation(String formattedLocation) {
            this.formattedLocation = formattedLocation;
            return this;
        }

        public Builder withCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public GeocodedLocation build() {
            return new GeocodedLocation(this);
        }

    }
    // generated code ends here

}
