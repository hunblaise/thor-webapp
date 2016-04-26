package com.balazs.hajdu.domain.repository.weather.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather information.
 * The object structure based on the openweathermap's JSON contract.
 *
 * @author Balazs Hajdu
 */
public class Cloud {

    Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cloud cloud = (Cloud) o;
        return Objects.equal(all, cloud.all);
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
