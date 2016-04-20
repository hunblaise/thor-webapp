package com.balazs.hajdu.domain.repository.forecast.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather forecast data.
 *
 * @author Balazs Hajdu
 */
public class ForecastClouds {

    private Double all;

    public Double getAll() {
        return all;
    }

    public void setAll(Double all) {
        this.all = all;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastClouds that = (ForecastClouds) o;
        return Objects.equal(all, that.all);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(all);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("all", all)
                .toString();
    }

}
