package com.balazs.hajdu.scheduled;

import com.balazs.hajdu.domain.repository.geo.UserLocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Balazs Hajdu
 */
@Service
public class WeatherLogger implements InitializingBean {

    private static final Long WEATHER_LOGGING_PERIOD = 10L;

    @Inject
    private ScheduledExecutorService backgroundTaskScheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
        WeatherLoggingTask weatherLoggingTask = new WeatherLoggingTask(new AtomicReference<>(new UserLocation.Builder().build()));
        backgroundTaskScheduler.scheduleWithFixedDelay(weatherLoggingTask, 0L, WEATHER_LOGGING_PERIOD, TimeUnit.SECONDS);
    }

}
