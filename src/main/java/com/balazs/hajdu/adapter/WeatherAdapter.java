package com.balazs.hajdu.adapter;

import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.weather.Weather;

/**
 * An adapter for the weather repositories.
 *
 * @author Balazs Hajdu
 */
public interface WeatherAdapter {

    /**
     * Get the current weather on the user's location.
     * @param cityName The name of the city.
     * @return current weather
     */
    Weather getCurrentWeather(String cityName);

    /**
     * Get weather forecast for the user's location.
     *
     * @param cityName The name of the city.
     * @return weather forecast
     */
    Forecast getForecastForCity(String cityName);

}
