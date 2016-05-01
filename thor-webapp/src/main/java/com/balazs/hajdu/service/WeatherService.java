package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.repository.forecast.FiveDayForecast;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.domain.response.WeatherSearchResponse;

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
     * Retrieves weather forecast for the user's location.
     *
     * @param cityName the name of the city.
     * @return weather forecast
     */
    FiveDayForecast getWeatherForecastForCity(String cityName);

}
