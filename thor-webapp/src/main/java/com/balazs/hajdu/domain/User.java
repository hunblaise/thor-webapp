package com.balazs.hajdu.domain;

import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * An immutable POJO to store user related information.
 * @author Balazs Hajdu
 */
public final class User {

    private final String username;
    private final String password;
    private final UserRoles userRole;
    private final GeocodedLocation location;

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.userRole = builder.userRole;
        this.location = builder.location;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public GeocodedLocation getLocation() {
        return location;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(username, user.username) &&
                Objects.equal(password, user.password) &&
                userRole == user.userRole &&
                Objects.equal(location, user.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, password, userRole, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("userRole", userRole)
                .add("location", location)
                .toString();
    }

    public static class Builder {

        private String username;
        private String password;
        private UserRoles userRole;
        private GeocodedLocation location;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withUserRole(UserRoles userRole) {
            this.userRole = userRole;
            return this;
        }

        public Builder withLocation(GeocodedLocation location) {
            this.location = location;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
    // generated code ends here

}
