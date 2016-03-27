package com.balazs.hajdu.service.impl;

import com.balazs.hajdu.adapter.GeoAdapter;
import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.service.WeatherService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Balazs Hajdu
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Inject
    private WeatherAdapter weatherAdapter;

    @Inject
    private GeoAdapter geoAdapter;

    @Override
    public Weather getCurrentWeather(String ipAddress) {
        UserLocation userLocation = geoAdapter.getUserLocation(ipAddress);

        return weatherAdapter.getCurrentWeather(userLocation.getCity());
    }
}
