package com.balazs.hajdu.domain.repository.forecast;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * An immutable POJO to store weather forecast.
 *
 * @author Balazs Hajdu
 */
public final class Forecast {

    private final List<ForecastDetail> details;

    private Forecast(Builder builder) {
        this.details = builder.details;
    }

    public List<ForecastDetail> getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return Objects.equal(details, forecast.details);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(details);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("details", details)
                .toString();
    }

    public static class Builder {

        private List<ForecastDetail> details;

        public Builder withDetails(List<ForecastDetail> details) {
            this.details = details;
            return this;
        }

        public Forecast build() {
            return new Forecast(this);
        }
    }

}
