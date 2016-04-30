package com.balazs.hajdu.domain.repository.forecast;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * An immutable POJO to store weather forecast.
 *
 * @author Balazs Hajdu
 */
public final class Forecast {

    private final List<ForecastDetail> details;
    private final Map<LocalDate, List<ForecastDetail>> detailsMap;

    private Forecast(Builder builder) {
        this.details = builder.details;
        this.detailsMap = builder.detailsMap;
    }

    public List<ForecastDetail> getDetails() {
        return details;
    }

    public Map<LocalDate, List<ForecastDetail>> getDetailsMap() {
        return detailsMap;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return Objects.equal(details, forecast.details) &&
                Objects.equal(detailsMap, forecast.detailsMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(details, detailsMap);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("details", details)
                .add("detailsMap", detailsMap)
                .toString();
    }

    public static class Builder {

        private List<ForecastDetail> details;
        private Map<LocalDate, List<ForecastDetail>> detailsMap;

        public Builder withDetails(List<ForecastDetail> details) {
            this.details = details;
            return this;
        }

        public Builder withDetailsMap(Map<LocalDate, List<ForecastDetail>> detailsMap) {
            this.detailsMap = detailsMap;
            return this;
        }

        public Forecast build() {
            return new Forecast(this);
        }
    }
    // generated code ends here

}
