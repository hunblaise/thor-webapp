package com.balazs.hajdu.adapter.impl;

import com.balazs.hajdu.components.factories.ForecastFactory;
import com.balazs.hajdu.components.transformers.WeatherTransformer;
import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.forecast.ForecastDetail;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastInformation;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastResponse;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastWeather;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.domain.repository.weather.response.CurrentWeather;
import com.balazs.hajdu.domain.repository.weather.response.WeatherType;
import com.balazs.hajdu.repository.WeatherRepository;
import com.google.common.collect.ImmutableList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Balazs Hajdu
 */
public class WeatherAdapterImplTest {

    private static final String TEST_CITY_NAME = "test-city-name";
    private static final Long TEST_CURRENT_WEATHER_ID = 1L;
    private static final String TEST_DESCRIPTION = "test-description";
    private static final String TEST_WEATHER_MAIN = "test-weather-main";
    private static final String TEST_ICON = "test-icon";
    private static final int TEST_FORECAST_INFORMATION_ID = 1;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private WeatherTransformer weatherTransformer;

    @Mock
    private ForecastFactory forecastFactory;

    @InjectMocks
    private WeatherAdapterImpl weatherAdapter;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCurrentWeather() {
        // given
        CurrentWeather currentWeather = aCurrentWeather();
        Weather weather = aWeather();
        // annd
        given(weatherRepository.getCurrentWeather(TEST_CITY_NAME)).willReturn(currentWeather);
        given(weatherTransformer.transform(currentWeather)).willReturn(weather);

        // when
        Weather actual = weatherAdapter.getCurrentWeather(TEST_CITY_NAME);

        // then
        verify(weatherRepository, times(1)).getCurrentWeather(TEST_CITY_NAME);
        verify(weatherTransformer, times(1)).transform(currentWeather);
        assertThat(actual, is(weather));
    }

    @Test
    public void shouldReturnForecast() {
        // given
        ForecastResponse forecastResponse = aForecastResponse();
        Forecast forecast = aForecast();
        // and
        given(weatherRepository.getForecastForCity(TEST_CITY_NAME)).willReturn(forecastResponse);
        given(forecastFactory.createForecastFrom(forecastResponse)).willReturn(forecast);

        // when
        Forecast actual = weatherAdapter.getForecastForCity(TEST_CITY_NAME);

        // then
        verify(weatherRepository, times(1)).getForecastForCity(TEST_CITY_NAME);
        verify(forecastFactory, times(1)).createForecastFrom(forecastResponse);
        assertThat(actual, is(forecast));
    }

    private Forecast aForecast() {
        return new Forecast.Builder().withDetails(forecastDetails()).build();
    }

    private List<ForecastDetail> forecastDetails() {
        return ImmutableList.of(new ForecastDetail.Builder().withDescription(TEST_DESCRIPTION)
                .withIcon(TEST_ICON).withId(TEST_FORECAST_INFORMATION_ID)
                .build());
    }

    private ForecastResponse aForecastResponse() {
        ForecastResponse forecastResponse = new ForecastResponse();

        forecastResponse.setList(forecastInformation());
        return null;
    }

    private List<ForecastInformation> forecastInformation() {
        ForecastInformation forecastInformation = new ForecastInformation();

        forecastInformation.setWeather(forecastWeather());

        return ImmutableList.of(forecastInformation);
    }

    private List<ForecastWeather> forecastWeather() {
        ForecastWeather forecastWeather = new ForecastWeather();

        forecastWeather.setIcon(TEST_ICON);
        forecastWeather.setMain(TEST_WEATHER_MAIN);
        forecastWeather.setDescription(TEST_DESCRIPTION);
        forecastWeather.setId(TEST_FORECAST_INFORMATION_ID);

        return ImmutableList.of(forecastWeather);
    }

    private Weather aWeather() {
        return new Weather.Builder().withIcon(TEST_ICON)
                .withDescription(TEST_DESCRIPTION)
                .withId(TEST_CURRENT_WEATHER_ID)
                .build();
    }

    private CurrentWeather aCurrentWeather() {
        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setId(TEST_CURRENT_WEATHER_ID);
        currentWeather.setWeather(weathers());

        return currentWeather;
    }

    private List<WeatherType> weathers() {
        WeatherType weatherType = new WeatherType();

        weatherType.setDescription(TEST_DESCRIPTION);
        weatherType.setMain(TEST_WEATHER_MAIN);
        weatherType.setIcon(TEST_ICON);
        weatherType.setId(TEST_CURRENT_WEATHER_ID);

        return ImmutableList.of(weatherType);
    }
}