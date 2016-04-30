package com.balazs.hajdu.service.impl;

import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.domain.repository.forecast.DailyForecast;
import com.balazs.hajdu.domain.repository.forecast.FiveDayForecast;
import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.forecast.ForecastDetail;
import com.balazs.hajdu.domain.repository.forecast.HourlyForecast;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.google.common.collect.ImmutableList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author Balazs Hajdu
 */
public class WeatherServiceImplTest {

    private static final String TEST_CITY_NAME = "test-city-name";
    private static final double TEST_TEMPERATURE = 26;
    private static final String TEST_ICON = "test-icon";
    private static final long TEST_ID = 321;
    private static final String TEST_DESCRIPTION = "test-description";
    private static final long TEST_HUMIDITY = 2345;
    private static final long TEST_PRESSURE = 343225;
    private static final int TEST_FORECAST_INFORMATION_ID = 21;
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();
    private static final LocalDateTime TEST_DATE_PLUS_ONE_DAY = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime TEST_DATE_PLUS_TWO_DAY = LocalDateTime.now().plusDays(2);
    private static final double TEST_TEMPERATURE_MAX = 32;
    private static final double TEST_TEMPERATURE_MIN = 8;
    private static final double TEST_TEMPERATURE_BETWEEN = 10;
    private static final double TEST_TEMPERATURE_AVERAGE = 15.33;

    @Mock
    private WeatherAdapter weatherAdapter;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCurrentWeatherBasedOnCityName() {
        // given
        given(weatherAdapter.getCurrentWeather(TEST_CITY_NAME)).willReturn(aWeather());
        // and
        Weather expected = aWeather();

        // when
        Weather actual = weatherService.getCurrentWeather(TEST_CITY_NAME);

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnFiveDayForecastWithDailyForecasts() {
        // given
        given(weatherAdapter.getForecastForCity(TEST_CITY_NAME)).willReturn(aForecast());
        // and
        FiveDayForecast expected = aFiveDayForecastWithDailyForecast(TEST_TEMPERATURE_AVERAGE);

        // when
        FiveDayForecast actual = weatherService.getWeatherForecastForCity(TEST_CITY_NAME);

        // then
        assertThat(actual.getDailyForecasts(), is(expected.getDailyForecasts()));
    }

    @Test
    public void shouldReturnFiveDayForecastWithHourlyForecast() {
        // given
        given(weatherAdapter.getForecastForCity(TEST_CITY_NAME)).willReturn(aForecast());
        // and
        FiveDayForecast expected = aFiveDayForecastWithHourlyForecast();

        // when
        FiveDayForecast actual = weatherService.getWeatherForecastForCity(TEST_CITY_NAME);

        // then
        assertThat(actual.getHourlyForecasts(), is(expected.getHourlyForecasts()));
    }

    private FiveDayForecast aFiveDayForecastWithHourlyForecast() {
        return new FiveDayForecast.Builder()
                .withHourlyForecasts(ImmutableList.of(
                        aHourlyForecast(TEST_DATE),
                        aHourlyForecast(TEST_DATE_PLUS_ONE_DAY),
                        aHourlyForecast(TEST_DATE_PLUS_TWO_DAY)))
                .build();
    }

    private HourlyForecast aHourlyForecast(LocalDateTime localDateTime) {
        return new HourlyForecast.Builder()
                .withTemperature(TEST_TEMPERATURE)
                .withDate(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    private FiveDayForecast aFiveDayForecastWithDailyForecast(double averageTemperature) {
        return new FiveDayForecast.Builder()
                .withDailyForecasts(ImmutableList.of(aDailyForecast(averageTemperature)))
                .build();
    }

    private DailyForecast aDailyForecast(double averageTemperature) {
        return new DailyForecast.Builder()
                .withMinTemperature(TEST_TEMPERATURE_MIN)
                .withMaxTemperature(TEST_TEMPERATURE_MAX)
                .withTemperature(averageTemperature)
                .withId(TEST_FORECAST_INFORMATION_ID)
                .withDate(TEST_DATE.toLocalDate())
                .build();
    }

    private ForecastDetail aForecastDetail(double temperature, double maxTemperature, double minTemperature, LocalDateTime localDateTime) {
        return new ForecastDetail.Builder()
                .withId(TEST_FORECAST_INFORMATION_ID)
                .withIcon(TEST_ICON)
                .withDescription(TEST_DESCRIPTION)
                .withDate(localDateTime)
                .withHumidity(TEST_HUMIDITY)
                .withMaxTemperature(maxTemperature)
                .withMinTemperature(minTemperature)
                .withTemperature(temperature)
                .withPressure(TEST_PRESSURE)
                .build();
    }

    private Map<LocalDate, List<ForecastDetail>> aDetailsMap() {
        Map<LocalDate, List<ForecastDetail>> map = new TreeMap<>();

        map.put(TEST_DATE.toLocalDate(), ImmutableList.of(
                aForecastDetail(TEST_TEMPERATURE, TEST_TEMPERATURE_BETWEEN, TEST_TEMPERATURE_MIN, TEST_DATE),
                aForecastDetail(TEST_TEMPERATURE_BETWEEN, TEST_TEMPERATURE_BETWEEN, TEST_TEMPERATURE_BETWEEN, TEST_DATE),
                aForecastDetail(TEST_TEMPERATURE_BETWEEN, TEST_TEMPERATURE_MAX, TEST_TEMPERATURE_BETWEEN, TEST_DATE)));

        return map;
    }

    private Forecast aForecast() {
        return new Forecast.Builder()
                .withDetailsMap(aDetailsMap())
                .withDetails(ImmutableList.of(
                        aForecastDetail(TEST_TEMPERATURE, TEST_TEMPERATURE_MAX, TEST_TEMPERATURE_BETWEEN, TEST_DATE),
                        aForecastDetail(TEST_TEMPERATURE, TEST_TEMPERATURE_BETWEEN, TEST_TEMPERATURE_BETWEEN, TEST_DATE_PLUS_ONE_DAY),
                        aForecastDetail(TEST_TEMPERATURE, TEST_TEMPERATURE_BETWEEN, TEST_TEMPERATURE_MIN, TEST_DATE_PLUS_TWO_DAY)))
                .build();
    }

    private Weather aWeather() {
        return new Weather.Builder()
                .withTemperature(TEST_TEMPERATURE)
                .withIcon(TEST_ICON)
                .withId(TEST_ID)
                .withDescription(TEST_DESCRIPTION)
                .withHumidity(TEST_HUMIDITY)
                .withPressure(TEST_PRESSURE)
                .build();
    }

}