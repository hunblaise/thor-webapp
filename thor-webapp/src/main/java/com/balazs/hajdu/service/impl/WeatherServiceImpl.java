package com.balazs.hajdu.service.impl;

import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.domain.repository.forecast.DailyForecast;
import com.balazs.hajdu.domain.repository.forecast.FiveDayForecast;
import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.forecast.ForecastDetail;
import com.balazs.hajdu.domain.repository.forecast.HourlyForecast;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.service.WeatherService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A service to handle weather related operations.
 * @author Balazs Hajdu
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private static final int DECIMAL_PLACES = 2;

    @Inject
    private WeatherAdapter weatherAdapter;

    @Override
    public Weather getCurrentWeather(String cityName) {
        return weatherAdapter.getCurrentWeather(cityName);
    }

    @Override
    public FiveDayForecast getWeatherForecastForCity(String cityName) {
        Forecast forecast = weatherAdapter.getForecastForCity(cityName);

        return new FiveDayForecast.Builder()
                .withDailyForecasts(populateDailyForecast(forecast.getDetailsMap()))
                .withHourlyForecasts(populateHourlyForecast(forecast.getDetails()))
                .build();
    }

    private List<HourlyForecast> populateHourlyForecast(List<ForecastDetail> details) {
        return details.stream()
                .map(this::buildHourlyForecast)
                .collect(Collectors.toList());
    }

    private HourlyForecast buildHourlyForecast(ForecastDetail forecastDetail) {
        return new HourlyForecast.Builder()
                .withDate(forecastDetail.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .withTemperature(forecastDetail.getTemperature())
                .build();
    }

    private List<DailyForecast> populateDailyForecast(Map<LocalDate, List<ForecastDetail>> details) {
        Map<LocalDate, List<ForecastDetail>> sortedMap = new TreeMap<>(details);

        return sortedMap.values().stream()
                .map(this::buildDailyForecast)
                 .collect(Collectors.toList());
    }

    private DailyForecast buildDailyForecast(List<ForecastDetail> details) {
        return new DailyForecast.Builder()
                .withDate(details.iterator().next().getDate().toLocalDate())
                .withId(details.iterator().next().getId())
                .withTemperature(new BigDecimal(details.stream()
                            .map(ForecastDetail::getTemperature)
                            .mapToDouble(t ->  t)
                            .average()
                            .getAsDouble())
                        .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP)
                        .doubleValue())
                .withMaxTemperature(details.stream()
                        .map(ForecastDetail::getMaxTemperature)
                        .max(Double::compare).orElse(0.0))
                .withMinTemperature(details.stream()
                        .map(ForecastDetail::getMinTemperature)
                        .min(Double::compare).orElse(0.0))
                .build();
    }

}
