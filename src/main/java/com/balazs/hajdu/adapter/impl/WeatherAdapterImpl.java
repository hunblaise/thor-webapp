package com.balazs.hajdu.adapter.impl;

import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.components.factories.ForecastFactory;
import com.balazs.hajdu.components.transformers.WeatherTransformer;
import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.repository.WeatherRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Default implementation of {@link com.balazs.hajdu.adapter.WeatherAdapter}.
 *
 * @author Balazs Hajdu
 */
@Component
public class WeatherAdapterImpl implements WeatherAdapter {

    @Inject
    private WeatherRepository weatherRepository;

    @Inject
    private WeatherTransformer weatherTransformer;

    @Inject
    private ForecastFactory forecastFactory;

    @Override
    public Weather getCurrentWeather(String cityName) {
        return weatherTransformer.transform(weatherRepository.getCurrentWeather(cityName));
    }

    @Override
    public Forecast getForecastForCity(String cityName) {
        return forecastFactory.createForecastFrom(weatherRepository.getForecastForCity(cityName));
    }
}
