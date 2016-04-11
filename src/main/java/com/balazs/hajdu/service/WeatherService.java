package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.weather.Weather;

import java.net.UnknownHostException;

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
    Weather getCurrentWeather(String ipAddress) throws UnknownHostException;

    /**
     * Retrieves the current weather on the user's location.
     *
     * @param userLocation location
     * @return the current weather
     */
    Weather getCurrentWeather(UserLocation userLocation);

}
