package com.balazs.hajdu.domain.repository;

import com.balazs.hajdu.domain.AbstractDocument;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * @author Balazs Hajdu
 */
@Document(collection = "home-control")
public class UserEntity extends AbstractDocument {

    @Indexed(unique = true, direction = IndexDirection.ASCENDING, dropDups = true)
    private String username;
    private String password;
    private Instant created;
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equal(username, that.username) &&
                Objects.equal(password, that.password) &&
                Objects.equal(role, that.role) &&
                Objects.equal(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), username, password, role, created);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("role", role)
                .add("created", created)
                .toString();
    }

}
