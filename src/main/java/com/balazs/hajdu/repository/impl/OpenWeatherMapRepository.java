package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.domain.repository.weather.CurrentWeather;
import com.balazs.hajdu.repository.WeatherRepository;
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

    private static final String APP_ID_PARAM = "appid";
    private static final String QUERY_PARAM = "q";
    private static final String UNITS_PARAM = "units";
    private static final String UNIT_VALUE = "metric";

    @Value("${WEATHER_API_ENDPOINT}")
    private String url;

    @Value("${WEATHER_API_KEY}")
    private String key;

    @Inject
    private RestTemplate restTemplate;

    @Override
    public CurrentWeather getCurrentWeather(String cityName) {
        return restTemplate.getForObject(buildUrl(cityName), CurrentWeather.class);
    }

    @Override
    public CurrentWeather getCurrentWeather(Long cityId) {
        return null;
    }

    private String buildUrl(String cityName) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(3);
        parameters.add(QUERY_PARAM, cityName);
        parameters.add(UNITS_PARAM, UNIT_VALUE);
        parameters.add(APP_ID_PARAM, key);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(parameters).build();

        return uriComponents.toUriString();

    }

}
