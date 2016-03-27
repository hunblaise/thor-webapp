package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.repository.weather.CurrentWeather;

/**
 * An interface to access weather information.
 *
 * @author Balazs Hajdu
 */
public interface WeatherRepository {

    CurrentWeather getCurrentWeather(String cityName);

    CurrentWeather getCurrentWeather(Long cityId);

}
