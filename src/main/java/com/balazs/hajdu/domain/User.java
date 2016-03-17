package com.balazs.hajdu.domain;

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

    private User(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.userRole = builder.userRole;
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

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(username, user.username) &&
                Objects.equal(password, user.password) &&
                userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, password, userRole);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("userRole", userRole)
                .toString();
    }

    public static class Builder {

        private String username;
        private String password;
        private UserRoles userRole;

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

        public User build() {
            return new User(this);
        }

    }
    // generated code ends here

}
