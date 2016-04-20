package com.balazs.hajdu.service.impl;

import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.forecast.ForecastDetail;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.WeatherService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Balazs Hajdu
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Inject
    private WeatherAdapter weatherAdapter;

    @Inject
    private UserLocationService userLocationService;

    @Override
    public Weather getCurrentWeather(String cityName) {
        return weatherAdapter.getCurrentWeather(cityName);
    }

    @Override
    public Weather getCurrentWeather(UserLocation userLocation) {
        return weatherAdapter.getCurrentWeather(userLocation.getCity());
    }

    @Override
    public Forecast getWeatherForecastForCity(String cityName) {
        Set<LocalDateTime> nextFiveDay = calculateNextFiveDays();
        Forecast forecast = weatherAdapter.getForecastForCity(cityName);

        List<ForecastDetail> forecastDetails = forecast.getDetails().stream()
                .filter(forecastInformation -> nextFiveDay.contains(forecastInformation.getDate()))
                .collect(Collectors.toList());

        return new Forecast.Builder().withDetails(forecastDetails).build();
    }

    private Set<LocalDateTime> calculateNextFiveDays() {
        Set<LocalDateTime> nextFiveDay = new HashSet<>(5);

        for (int i = 1; i < 6; i++) {
            nextFiveDay.add(LocalDateTime.now().plusDays(i).withHour(12).withMinute(0).withSecond(0).withNano(0));
        }

        return nextFiveDay;
    }

}
