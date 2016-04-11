package com.balazs.hajdu.domain.view;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to save sensor into the database.
 *
 * @author Balazs Hajdu
 */
public class SensorRequestForm {

    private String sensorName;
    private String address;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorRequestForm that = (SensorRequestForm) o;
        return Objects.equal(sensorName, that.sensorName) &&
                Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sensorName, address);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sensorName", sensorName)
                .add("address", address)
                .toString();
    }
    // generated code ends here

}
