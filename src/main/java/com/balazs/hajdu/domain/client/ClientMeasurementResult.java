package com.balazs.hajdu.domain.client;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to represents the raspberry client's response.
 *
 * @author Balazs Hajdu
 */
public class ClientMeasurementResult {

    private double value;
    private String error;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientMeasurementResult that = (ClientMeasurementResult) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equal(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, error);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("error", error)
                .toString();
    }
    // generated code ends here

}
