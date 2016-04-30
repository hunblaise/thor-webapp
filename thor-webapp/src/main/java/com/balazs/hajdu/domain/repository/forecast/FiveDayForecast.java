package com.balazs.hajdu.domain.repository.forecast;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * An immutable POJO to store to store weather details for the next five days.
 *
 * @author Balazs Hajdu
 */
public final class FiveDayForecast {

    private final List<HourlyForecast> hourlyForecasts;
    private final List<DailyForecast> dailyForecasts;

    private FiveDayForecast(Builder builder) {
        this.hourlyForecasts = builder.hourlyForecasts;
        this.dailyForecasts = builder.dailyForecasts;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiveDayForecast that = (FiveDayForecast) o;
        return Objects.equal(hourlyForecasts, that.hourlyForecasts) &&
                Objects.equal(dailyForecasts, that.dailyForecasts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hourlyForecasts, dailyForecasts);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hourlyForecasts", hourlyForecasts)
                .add("dailyForecasts", dailyForecasts)
                .toString();
    }

    public static class Builder {

        private List<HourlyForecast> hourlyForecasts;
        private List<DailyForecast> dailyForecasts;

        public Builder withHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
            this.hourlyForecasts = hourlyForecasts;
            return this;
        }

        public Builder withDailyForecasts(List<DailyForecast> dailyForecasts) {
            this.dailyForecasts = dailyForecasts;
            return this;
        }

        public FiveDayForecast build() {
            return new FiveDayForecast(this);
        }
    }
    // generated code ends here

}
