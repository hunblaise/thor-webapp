package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.weather.Weather;

/**
 * A weather related interface.
 *
 * @author Balazs Hajdu
 */
public interface WeatherService {

    /**
     * Retrieves the current weather on the user's location.
     *
     * @param cityName ip address.
     * @return The current weather.
     */
    Weather getCurrentWeather(String cityName);

    /**
     * Retrieves the current weather on the user's location.
     *
     * @param userLocation location
     * @return the current weather
     */
    Weather getCurrentWeather(UserLocation userLocation);

    /**
     * Retrieves weather forecast for the user's location.
     *
     * @param cityName the name of the city.
     * @return weather forecast
     */
    Forecast getWeatherForecastForCity(String cityName);

}
