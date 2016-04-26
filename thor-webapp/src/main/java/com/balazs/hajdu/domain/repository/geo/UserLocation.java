package com.balazs.hajdu.domain.repository.geo;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store user location;
 *
 * @author Balazs Hajdu
 */
public final class UserLocation {

    private final String country;
    private final String city;
    private final double lat;
    private final double lon;

    public UserLocation(Builder builder) {
        this.country = builder.country;
        this.city = builder.city;
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
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
        UserLocation that = (UserLocation) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lon, lon) == 0 &&
                Objects.equal(country, that.country) &&
                Objects.equal(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(country, city, lat, lon);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("country", country)
                .add("city", city)
                .add("lat", lat)
                .add("lon", lon)
                .toString();
    }

    public static class Builder {

        private String country;
        private String city;
        private double lat;
        private double lon;

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withLatitude(double latitude) {
            this.lat = latitude;
            return this;
        }

        public Builder withLongitude(double longitude) {
            this.lon = longitude;
            return this;
        }

        public UserLocation build() {
            return new UserLocation(this);
        }

    }
    // generated code ends here

}
