package com.balazs.hajdu.domain.repository.weather;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather information.
 * The object structure based on the openweathermap's JSON contract.
 *
 * @author Balazs Hajdu
 */
public class Snow {

    Integer snowVolume;

    public Integer getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(Integer snowVolume) {
        this.snowVolume = snowVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snow snow = (Snow) o;
        return Objects.equal(snowVolume, snow.snowVolume);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(snowVolume);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("snowVolume", snowVolume)
                .toString();
    }

}
