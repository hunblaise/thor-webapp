package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.domain.repository.weather.response.CurrentWeather;
import com.balazs.hajdu.domain.repository.weather.response.WeatherInformation;
import com.balazs.hajdu.domain.repository.weather.response.WeatherType;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class WeatherTransformerTest {

    private static final double TEST_TEMPERATURE = 35;
    private static final long TEST_PRESSURE = 3456;
    private static final long TEST_HUMIDITY = 60;
    private static final String TEST_DESCRIPTION = "test-description";
    private static final long TEST_ID = 321;
    private static final String TEST_ICON = "test-icon";

    private WeatherTransformer weatherTransformer;

    @BeforeMethod
    public void setUp() throws Exception {
        weatherTransformer = new WeatherTransformer();
    }

    @Test
    public void shouldTransformWeatherWithPropertiesIfPresent() {
        // given
        Weather expected = aWeather();

        // when
        Weather actual = weatherTransformer.transform(aCurrentWeather());

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldTransformWithoutTypeIfEmpty() {
        // given
        Weather expected = aWeatherWithoutType();

        // when
        Weather actual = weatherTransformer.transform(aCurrentWeatherWithoutType());

        // then
        assertThat(actual.getDescription(), is(nullValue()));
        assertThat(actual.getId(), is(nullValue()));
        assertThat(actual.getIcon(), is(nullValue()));
        assertThat(actual, is(expected));
    }

    private CurrentWeather aCurrentWeatherWithoutType() {
        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setMain(aWeatherInformation());
        currentWeather.setWeather(Collections.emptyList());

        return currentWeather;
    }

    private Weather aWeatherWithoutType() {
        return new Weather.Builder().withTemperature(TEST_TEMPERATURE)
                .withPressure(TEST_PRESSURE)
                .withHumidity(TEST_HUMIDITY)
                .build();
    }

    private CurrentWeather aCurrentWeather() {
        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setWeather(ImmutableList.of(aWeatherType()));
        currentWeather.setMain(aWeatherInformation());

        return currentWeather;
    }

    private WeatherInformation aWeatherInformation() {
        WeatherInformation weatherInformation = new WeatherInformation();

        weatherInformation.setTemp(TEST_TEMPERATURE);
        weatherInformation.setPressure(TEST_PRESSURE);
        weatherInformation.setHumidity(TEST_HUMIDITY);

        return weatherInformation;
    }

    private WeatherType aWeatherType() {
        WeatherType weatherType = new WeatherType();

        weatherType.setIcon(TEST_ICON);
        weatherType.setDescription(TEST_DESCRIPTION);
        weatherType.setId(TEST_ID);

        return weatherType;
    }

    private Weather aWeather() {
        return new Weather.Builder().withTemperature(TEST_TEMPERATURE)
                .withPressure(TEST_PRESSURE)
                .withHumidity(TEST_HUMIDITY)
                .withDescription(TEST_DESCRIPTION)
                .withId(TEST_ID)
                .withIcon(TEST_ICON)
                .build();
    }

}