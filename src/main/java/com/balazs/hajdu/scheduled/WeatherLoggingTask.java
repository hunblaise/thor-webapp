package com.balazs.hajdu.scheduled;

import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Balazs Hajdu
 */
public class WeatherLoggingTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherLoggingTask.class);

    private AtomicReference<UserLocation> location;

    public WeatherLoggingTask(AtomicReference<UserLocation> location) {
        this.location = location;
    }

    @Inject
    WeatherService weatherService;

    @Override
    public void run() {
        Weather weather = weatherService.getCurrentWeather(location.get());
        LOGGER.error("Current weather: " + weather);
    }
}
