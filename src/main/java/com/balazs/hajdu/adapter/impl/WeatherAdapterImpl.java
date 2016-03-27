package com.balazs.hajdu.adapter.impl;

import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.components.transformers.WeatherTransformer;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.repository.WeatherRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Balazs Hajdu
 */
@Component
public class WeatherAdapterImpl implements WeatherAdapter {

    @Inject
    private WeatherRepository weatherRepository;

    @Inject
    private WeatherTransformer weatherTransformer;

    @Override
    public Weather getCurrentWeather(String cityName) {
        return weatherTransformer.tranform(weatherRepository.getCurrentWeather(cityName));
    }
}
