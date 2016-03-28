package com.balazs.hajdu.domain.view;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store temperature related data.
 *
 * @author Balazs Hajdu
 */
public class SensorValue {

    double value;
    String name;
    SensorLocation location;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorLocation getLocation() {
        return location;
    }

    public void setLocation(SensorLocation location) {
        this.location = location;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorValue that = (SensorValue) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equal(name, that.name) &&
                Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, name, location);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("name", name)
                .add("location", location)
                .toString();
    }
    // generated code ends here

}
