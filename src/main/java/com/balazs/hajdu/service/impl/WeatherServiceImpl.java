package com.balazs.hajdu.service.impl;

import com.balazs.hajdu.adapter.GeoAdapter;
import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.WeatherService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.UnknownHostException;

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
    public Weather getCurrentWeather(String ipAddress) throws UnknownHostException {
        UserLocation userLocation = userLocationService.getUserLocation(ipAddress);

        return weatherAdapter.getCurrentWeather(userLocation.getCity());
    }

    @Override
    public Weather getCurrentWeather(UserLocation userLocation) {
        return weatherAdapter.getCurrentWeather(userLocation.getCity());
    }

}
