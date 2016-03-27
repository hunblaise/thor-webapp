package com.balazs.hajdu.service;

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
     * @param ipAddress ip address.
     * @return The current weather.
     */
    Weather getCurrentWeather(String ipAddress);

}
