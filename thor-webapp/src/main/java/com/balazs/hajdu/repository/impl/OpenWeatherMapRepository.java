package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.domain.repository.forecast.response.ForecastResponse;
import com.balazs.hajdu.domain.repository.weather.response.CurrentWeather;
import com.balazs.hajdu.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

/**
 * @author Balazs Hajdu
 */
@Repository
public class OpenWeatherMapRepository implements WeatherRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapRepository.class);

    private static final String APP_ID_PARAM = "appid";
    private static final String QUERY_PARAM = "q";
    private static final String UNITS_PARAM = "units";
    private static final String UNIT_VALUE = "metric";

    @Value("${WEATHER_API_ENDPOINT}")
    private String weatherHost;

    @Value("${WEATHER_API_KEY}")
    private String weatherKey;

    @Value("${FORECAST_API_KEY}")
    private String forecastKey;

    @Value(("${FORECAST_API_ENDPOINT}"))
    private String forecastHost;

    @Inject
    private RestTemplate restTemplate;

    @Override
    public CurrentWeather getCurrentWeather(String cityName) {
        String url = buildUrl(weatherHost, weatherKey, cityName);

        LOGGER.debug("Retrieving current weather from url: {}", url);

        return restTemplate.getForObject(url, CurrentWeather.class);
    }

    @Override
    public CurrentWeather getCurrentWeather(Long cityId) {
        return null;
    }

    @Override
    public ForecastResponse getForecastForCity(String cityName) {
        String url = buildUrl(forecastHost, forecastKey, cityName);

        LOGGER.debug("Retrieving weather forecast with url: {}", url);

        return restTemplate.getForObject(url, ForecastResponse.class);
    }

    private String buildUrl(String host, String key, String cityName) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(3);
        parameters.add(QUERY_PARAM, cityName);
        parameters.add(UNITS_PARAM, UNIT_VALUE);
        parameters.add(APP_ID_PARAM, key);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(host)
                .queryParams(parameters)
                .build();

        return uriComponents.toUriString();
    }

}
