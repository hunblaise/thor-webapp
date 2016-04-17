package com.balazs.hajdu.client.domain.request;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to map measurement results request.
 *
 * @author Balazs Hajdu
 */
public class MeasurementRequest {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementRequest that = (MeasurementRequest) o;
        return Objects.equal(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("key", key)
                .toString();
    }
}