package com.balazs.hajdu.domain.repository.weather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather information.
 * The object structure based on the openweathermap's JSON contract.
 *
 * @author Balazs Hajdu
 */
public class Rain {

    @JsonProperty(value = "3h")
    Integer rainVolume;

    public Integer getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(Integer rainVolume) {
        this.rainVolume = rainVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rain rain = (Rain) o;
        return Objects.equal(rainVolume, rain.rainVolume);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rainVolume);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("rainVolume", rainVolume)
                .toString();
    }

}
