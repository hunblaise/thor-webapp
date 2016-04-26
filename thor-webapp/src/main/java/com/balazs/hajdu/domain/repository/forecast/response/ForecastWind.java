package com.balazs.hajdu.domain.repository.forecast.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather forecast information.
 *
 * @author Balazs Hajdu
 */
public class ForecastWind {

    private Double speed;
    private Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastWind that = (ForecastWind) o;
        return Objects.equal(speed, that.speed) &&
                Objects.equal(deg, that.deg);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(speed, deg);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("speed", speed)
                .add("deg", deg)
                .toString();
    }

}
