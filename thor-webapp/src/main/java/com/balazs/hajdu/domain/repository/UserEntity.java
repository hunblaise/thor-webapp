package com.balazs.hajdu.domain.repository;

import com.balazs.hajdu.domain.AbstractDocument;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A representation of the user in the database.
 *
 * @author Balazs Hajdu
 */
@Document(collection = "users")
public class UserEntity extends AbstractDocument {

    @Indexed(unique = true, direction = IndexDirection.ASCENDING, dropDups = true)
    @TextIndexed(weight = 3)
    private String username;
    private String password;
    private LocalDateTime created;
    private String role;
    @TextIndexed(weight = 2)
    private String address;
    private GeoJsonPoint location;
    @TextIndexed
    private List<SensorEntity> sensors;

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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<SensorEntity> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorEntity> sensors) {
        this.sensors = sensors;
    }

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
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equal(username, that.username) &&
                Objects.equal(password, that.password) &&
                Objects.equal(created, that.created) &&
                Objects.equal(role, that.role) &&
                Objects.equal(sensors, that.sensors) &&
                Objects.equal(address, that.address) &&
                Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), username, password, created, role, sensors, address, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("password", password)
                .add("created", created)
                .add("role", role)
                .add("sensors", sensors)
                .add("address", address)
                .add("location", location)
                .toString();
    }
    // generated code ends here

}
