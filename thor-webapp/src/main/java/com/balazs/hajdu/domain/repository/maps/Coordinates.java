package com.balazs.hajdu.domain.repository.maps;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author Balazs Hajdu
 */
public final class Coordinates {

    private final double lat;
    private final double lon;

    private Coordinates(Builder builder) {
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return Double.compare(coordinates.lat, lat) == 0 &&
                Double.compare(coordinates.lon, lon) == 0;
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

    public static class Builder {

        private double lat;
        private double lon;

        public Builder withLattitude(double lat) {
            this.lat = lat;
            return this;
        }

        public Builder withLongitude(double lon) {
            this.lon = lon;
            return this;
        }

        public Coordinates build() {
            return new Coordinates(this);
        }

    }
    // generated code ends here

}
