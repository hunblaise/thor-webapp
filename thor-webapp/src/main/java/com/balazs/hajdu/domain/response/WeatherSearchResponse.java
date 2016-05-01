package com.balazs.hajdu.domain.response;

import com.balazs.hajdu.domain.repository.forecast.DailyForecast;
import com.balazs.hajdu.domain.repository.forecast.HourlyForecast;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * An immutable POJO to store weather search response.
 *
 * @author Balazs Hajdu
 */
public final class WeatherSearchResponse {

    private final String city;
    private final List<DailyForecast> dailyForecasts;
    private final List<HourlyForecast> hourlyForecasts;
    private final MeasurementResultStatistics statistics;

    private WeatherSearchResponse(Builder builder) {
        this.city = builder.city;
        this.dailyForecasts = builder.dailyForecasts;
        this.hourlyForecasts = builder.hourlyForecasts;
        this.statistics = builder.statistics;
    }

    public String getCity() {
        return city;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public MeasurementResultStatistics getStatistics() {
        return statistics;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherSearchResponse that = (WeatherSearchResponse) o;
        return Objects.equal(city, that.city) &&
                Objects.equal(dailyForecasts, that.dailyForecasts) &&
                Objects.equal(hourlyForecasts, that.hourlyForecasts) &&
                Objects.equal(statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(city, dailyForecasts, hourlyForecasts, statistics);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("city", city)
                .add("dailyForecasts", dailyForecasts)
                .add("hourlyForecasts", hourlyForecasts)
                .add("statistics", statistics)
                .toString();
    }

    public static class Builder {

        private String city;
        private List<DailyForecast> dailyForecasts;
        private List<HourlyForecast> hourlyForecasts;
        private MeasurementResultStatistics statistics;

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withDailyForecasts(List<DailyForecast> dailyForecasts) {
            this.dailyForecasts = dailyForecasts;
            return this;
        }

        public Builder withHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
            this.hourlyForecasts = hourlyForecasts;
            return this;
        }

        public Builder withStatistics(MeasurementResultStatistics statistics) {
            this.statistics = statistics;
            return this;
        }

        public WeatherSearchResponse build() {
            return new WeatherSearchResponse(this);
        }
    }
    // generated code ends here

}
