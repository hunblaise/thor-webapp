package com.balazs.hajdu.components.factories;

import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.forecast.ForecastDetail;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastInformation;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastMain;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastResponse;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastWeather;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class ForecastFactoryTest {

    private static final String TEST_ICON = "test-icon";
    private static final String TEST_WEATHER_MAIN = "test-weather-main";
    private static final String TEST_DESCRIPTION = "test-weather-description";
    private static final int TEST_FORECAST_INFORMATION_ID = 1;
    private static final double TEST_TEMP_MAX = 25;
    private static final double TEST_TEMP = 22;
    private static final double TEST_TEMP_MIN = 18;
    private static final double TEST_HUMIDITY = 60;
    private static final double TEST_PRESSURE = 321;
    private static final LocalDateTime NOW = LocalDateTime.now();

    private ForecastFactory forecastFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        forecastFactory = new ForecastFactory();
    }

    @Test
    public void shouldCreateForecastWithInformationIfPresent() {
        // given
        ForecastResponse forecastResponse = aForecastResponse();
        Forecast expected = aForecastWithWeatherInformationDetailsAndForecastMap();

        // when
        Forecast actual = forecastFactory.createForecastFrom(forecastResponse);

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldCreateForecastWithoutWeatherInformationIfEmpty() {
        // given
        ForecastResponse forecastResponse = aForecastResponseWithoutInformation();

        // when
        Forecast actual = forecastFactory.createForecastFrom(forecastResponse);

        // then
        assertThat(actual.getDetails().get(0).getDescription(), is(nullValue()));
        assertThat(actual.getDetails().get(0).getIcon(), is(nullValue()));
        assertThat(actual.getDetails().get(0).getId(), is(0));
    }

    private ForecastResponse aForecastResponseWithoutInformation() {
        ForecastResponse forecastResponse = new ForecastResponse();
        forecastResponse.setList(forecastInformationWithoutWeatherInformation());
        return forecastResponse;
    }

    private List<ForecastInformation> forecastInformationWithoutWeatherInformation() {
        ForecastInformation forecastInformation = new ForecastInformation();

        forecastInformation.setMain(aForecastMain());
        forecastInformation.setDate(NOW);
        forecastInformation.setWeather(Collections.emptyList());

        return ImmutableList.of(forecastInformation);
    }

    private ForecastDetail aForecastDetail() {
        return new ForecastDetail.Builder()
                .withId(TEST_FORECAST_INFORMATION_ID)
                .withIcon(TEST_ICON)
                .withDescription(TEST_DESCRIPTION)
                .withDate(NOW)
                .withHumidity(TEST_HUMIDITY)
                .withMaxTemperature(TEST_TEMP_MAX)
                .withMinTemperature(TEST_TEMP_MIN)
                .withTemperature(TEST_TEMP)
                .withPressure(TEST_PRESSURE)
                .build();
    }

    private Forecast aForecastWithWeatherInformationDetailsAndForecastMap() {
        return new Forecast.Builder()
                .withDetails(ImmutableList.of(aForecastDetail()))
                .withDetailsMap(aDetailsMap())
                .build();
    }

    private Map<LocalDate, List<ForecastDetail>> aDetailsMap() {
        Map<LocalDate, List<ForecastDetail>> map = new TreeMap<>();

        map.put(NOW.toLocalDate(), ImmutableList.of(aForecastDetail()));

        return map;
    }

    private ForecastResponse aForecastResponse() {
        ForecastResponse forecastResponse = new ForecastResponse();

        forecastResponse.setList(forecastInformation());

        return forecastResponse;
    }

    private List<ForecastInformation> forecastInformation() {
        ForecastInformation forecastInformation = new ForecastInformation();

        forecastInformation.setWeather(forecastWeather());
        forecastInformation.setMain(aForecastMain());
        forecastInformation.setDate(NOW);

        return ImmutableList.of(forecastInformation);
    }

    private ForecastMain aForecastMain() {
        ForecastMain forecastMain = new ForecastMain();

        forecastMain.setTempMax(TEST_TEMP_MAX);
        forecastMain.setTemp(TEST_TEMP);
        forecastMain.setTempMin(TEST_TEMP_MIN);
        forecastMain.setHumidity(TEST_HUMIDITY);
        forecastMain.setPressure(TEST_PRESSURE);

        return forecastMain;
    }

    private List<ForecastWeather> forecastWeather() {
        ForecastWeather forecastWeather = new ForecastWeather();

        forecastWeather.setIcon(TEST_ICON);
        forecastWeather.setMain(TEST_WEATHER_MAIN);
        forecastWeather.setDescription(TEST_DESCRIPTION);
        forecastWeather.setId(TEST_FORECAST_INFORMATION_ID);

        return ImmutableList.of(forecastWeather);
    }

}