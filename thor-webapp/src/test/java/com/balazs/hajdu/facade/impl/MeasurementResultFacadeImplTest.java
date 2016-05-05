package com.balazs.hajdu.facade.impl;

import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.forecast.DailyForecast;
import com.balazs.hajdu.domain.repository.forecast.FiveDayForecast;
import com.balazs.hajdu.domain.repository.forecast.HourlyForecast;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.balazs.hajdu.domain.response.WeatherSearchResponse;
import com.balazs.hajdu.domain.view.DateIntervalRequestForm;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.WeatherSearchQueryForm;
import com.balazs.hajdu.service.MeasurementResultService;
import com.balazs.hajdu.service.StatisticsService;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.WeatherService;
import com.google.common.collect.ImmutableList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Balazs Hajdu
 */
public class MeasurementResultFacadeImplTest {

    private static final double TEST_MEASUREMENT_RESULT_VALUE = 23;
    private static final double TEST_LOCATION_LATITUDE = 46.21;
    private static final double TEST_LOCATION_LONGITUDE = 20.10;
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();
    private static final String TEST_USERNAME = "test-username";
    private static final String TEST_SENSOR_NAME = "test-sensor-name";
    private static final String TEST_TYPE = "temperature";
    private static final String TEST_USER_ADDRESS = "test-user-address";
    private static final String TEST_FORMATTED_LOCATION = "test-formatted-location";
    private static final double TEST_TEMPERATURE = 23;
    private static final double TEST_TEMPERATURE_MIN = 8;
    private static final double TEST_TEMPERATURE_MAX = 32;
    private static final double TEST_AVERAGE_TEMPERATURE = 21;
    private static final int TEST_FORECAST_INFORMATION_ID = 1;

    @Mock
    private MeasurementResultService measurementResultService;

    @Mock
    private MeasurementResultTransformer measurementResultTransformer;

    @Mock
    private UserLocationService userLocationService;

    @Mock
    private WeatherService weatherService;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private MeasurementResultFacadeImpl measurementResultFacade;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveMeasurementResult() {
        // given
        MeasurementResultRequestForm requestForm = aRequestForm();
        // and
        MeasurementResult expected = aMeasurementResult();
        // and
        given(measurementResultService.saveMeasurementResultToSensor(any(MeasurementResult.class))).willReturn(expected);

        // when
        MeasurementResult actual = measurementResultFacade.saveMeasurementResult(TEST_USERNAME, TEST_SENSOR_NAME, Optional.of(requestForm));

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldReturnAllMeasurementResultForASensorAndUser() {
        // given
        List<MeasurementResultEntity> measurementResultEntities = ImmutableList.of(aMeasurementResultEntity());
        // and
        given(measurementResultService.getMeasurementResultsByUsernameAndSensorName(TEST_USERNAME, TEST_SENSOR_NAME)).willReturn(measurementResultEntities);
        // and
        List<MeasurementResponse> measurementResponses = ImmutableList.of(aMeasurementResponse());
        given(measurementResultTransformer.transformToResponse(measurementResultEntities)).willReturn(measurementResponses);

        // when
        List<MeasurementResponse> actual = measurementResultFacade.getAllMeasurementResultsForSensor(TEST_USERNAME, TEST_SENSOR_NAME);

        // then
        assertThat(actual, is(measurementResponses));
    }

    @Test
    public void shouldReturnMeasurementResultsFromDateInterval() {
        // given
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        // and
        DateIntervalRequestForm dateIntervalRequestForm = aDateIntervalRequestForm(startDate, endDate);
        //and
        MeasurementResultQueryContext queryContext = aMeasurementResultQueryContext(startDate, endDate);
        List<MeasurementResult> measurementResults = ImmutableList.of(aMeasurementResult());
        given(measurementResultService.getLastsMeasurementResultsFromDateInterval(queryContext)).willReturn(measurementResults);

        // and
        List<MeasurementResponse> measurementResponses = ImmutableList.of(aMeasurementResponse());
        given(measurementResultTransformer.transformThorToResponse(measurementResults)).willReturn(measurementResponses);

        // when
        List<MeasurementResponse> actual = measurementResultFacade.getMeasurementResultsFromDateInterval(TEST_USERNAME, TEST_SENSOR_NAME, dateIntervalRequestForm);

        // then
        assertThat(actual, is(measurementResponses));
    }

    @Test
    public void shouldNotReturnWeatherWhenUserLocationCanNotBeFound() {
        // given
        given(userLocationService.geocodeLocation(TEST_USER_ADDRESS)).willReturn(Collections.emptyList());
        // and
        WeatherSearchQueryForm weatherSearchQueryForm = aWeatherSearchQueryForm();

        // when
        Optional<WeatherSearchResponse> actual = measurementResultFacade.searchWeatherInLocation(weatherSearchQueryForm);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    public void shouldReturnWeatherInformationWithoutStatisticsWhenMeasurementResultsUnavailable() {
        // given
        List<GeocodedLocation> geocodedLocations = ImmutableList.of(aGeocodedLocation());
        given(userLocationService.geocodeLocation(TEST_USER_ADDRESS)).willReturn(geocodedLocations);
        // and
        given(weatherService.getWeatherForecastForCity(TEST_FORMATTED_LOCATION)).willReturn(aFiveDayForecast());
        // and
        given(statisticsService.getStatisticsForLocation(aGeocodedLocation())).willReturn(Optional.empty());

        // when
        Optional<WeatherSearchResponse> actual = measurementResultFacade.searchWeatherInLocation(aWeatherSearchQueryForm());

        // then
        assertTrue(actual.isPresent());
        assertThat(actual.get().getStatistics(), is(nullValue()));
        assertThat(actual.get().getHourlyForecasts(), is(ImmutableList.of(aHourlyForecast())));
        assertThat(actual.get().getDailyForecasts(), is(ImmutableList.of(aDailyForecast())));
        assertThat(actual.get().getCity(), is(TEST_FORMATTED_LOCATION));
    }

    @Test
    public void shouldReturnWeatherInformationWithStatistics() {
        // given
        List<GeocodedLocation> geocodedLocations = ImmutableList.of(aGeocodedLocation());
        given(userLocationService.geocodeLocation(TEST_USER_ADDRESS)).willReturn(geocodedLocations);
        // and
        given(weatherService.getWeatherForecastForCity(TEST_FORMATTED_LOCATION)).willReturn(aFiveDayForecast());
        // and
        given(statisticsService.getStatisticsForLocation(aGeocodedLocation())).willReturn(Optional.of(aStatistics()));

        // when
        Optional<WeatherSearchResponse> actual = measurementResultFacade.searchWeatherInLocation(aWeatherSearchQueryForm());

        // then
        assertTrue(actual.isPresent());
        assertThat(actual.get().getStatistics(), is(aStatistics()));
        assertThat(actual.get().getHourlyForecasts(), is(ImmutableList.of(aHourlyForecast())));
        assertThat(actual.get().getDailyForecasts(), is(ImmutableList.of(aDailyForecast())));
        assertThat(actual.get().getCity(), is(TEST_FORMATTED_LOCATION));
    }

    private MeasurementResultStatistics aStatistics() {
        return new MeasurementResultStatistics.Builder()
                .withAverage(TEST_AVERAGE_TEMPERATURE)
                .withMax(TEST_TEMPERATURE_MAX)
                .withMin(TEST_TEMPERATURE_MIN)
                .build();
    }

    private DailyForecast aDailyForecast() {
        return new DailyForecast.Builder()
                .withMinTemperature(TEST_TEMPERATURE_MIN)
                .withMaxTemperature(TEST_TEMPERATURE_MAX)
                .withTemperature(TEST_AVERAGE_TEMPERATURE)
                .withId(TEST_FORECAST_INFORMATION_ID)
                .withDate(TEST_DATE.toLocalDate())
                .build();
    }

    private HourlyForecast aHourlyForecast() {
        return new HourlyForecast.Builder()
                .withTemperature(TEST_TEMPERATURE)
                .withDate(TEST_DATE.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    private FiveDayForecast aFiveDayForecast() {
        return new FiveDayForecast.Builder()
                .withDailyForecasts(ImmutableList.of(aDailyForecast()))
                .withHourlyForecasts(ImmutableList.of(
                        aHourlyForecast()))
                .build();
    }

    private GeocodedLocation aGeocodedLocation() {
        return new GeocodedLocation.Builder()
                .withFormattedLocation(TEST_FORMATTED_LOCATION)
                .withCoordinates(new Coordinates.Builder()
                        .withLattitude(TEST_LOCATION_LATITUDE)
                        .withLongitude(TEST_LOCATION_LONGITUDE)
                        .build())
                .build();
    }

    private WeatherSearchQueryForm aWeatherSearchQueryForm() {
        WeatherSearchQueryForm weatherSearchQueryForm = new WeatherSearchQueryForm();

        weatherSearchQueryForm.setAddress(TEST_USER_ADDRESS);

        return weatherSearchQueryForm;
    }

    private DateIntervalRequestForm aDateIntervalRequestForm(LocalDateTime startDate, LocalDateTime endDate) {
        DateIntervalRequestForm dateIntervalRequestForm = new DateIntervalRequestForm();

        dateIntervalRequestForm.setStartDate(startDate);
        dateIntervalRequestForm.setEndDate(endDate);

        return dateIntervalRequestForm;
    }

    private MeasurementResultQueryContext aMeasurementResultQueryContext(LocalDateTime startDate, LocalDateTime endDate) {
        return new MeasurementResultQueryContext.Builder()
                .withUsername(TEST_USERNAME)
                .withSensorName(TEST_SENSOR_NAME)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }

    private MeasurementResponse aMeasurementResponse() {
        return new MeasurementResponse.Builder()
                .withValue(TEST_MEASUREMENT_RESULT_VALUE)
                .withDate(TEST_DATE.format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    private MeasurementResultEntity aMeasurementResultEntity() {
        MeasurementResultEntity measurementResultEntity = new MeasurementResultEntity();

        measurementResultEntity.setLocation(new GeoJsonPoint(TEST_LOCATION_LATITUDE, TEST_LOCATION_LONGITUDE));
        measurementResultEntity.setSensorName(TEST_SENSOR_NAME);
        measurementResultEntity.setUsername(TEST_USERNAME);
        measurementResultEntity.setValue(TEST_MEASUREMENT_RESULT_VALUE);
        measurementResultEntity.setDate(TEST_DATE);

        return measurementResultEntity;
    }

    private MeasurementResultRequestForm aRequestForm() {
        MeasurementResultRequestForm requestForm = new MeasurementResultRequestForm();

        requestForm.setLon(TEST_LOCATION_LONGITUDE);
        requestForm.setLat(TEST_LOCATION_LATITUDE);
        requestForm.setType(TEST_TYPE);
        requestForm.setValue(TEST_MEASUREMENT_RESULT_VALUE);

        return requestForm;
    }

    private MeasurementResult aMeasurementResult() {
        return new MeasurementResult.Builder()
                .withValue(TEST_MEASUREMENT_RESULT_VALUE)
                .withLocation(TEST_LOCATION_LATITUDE, TEST_LOCATION_LONGITUDE)
                .withDate(TEST_DATE)
                .withUsername(TEST_USERNAME)
                .withSensorName(TEST_SENSOR_NAME)
                .build();
    }

}