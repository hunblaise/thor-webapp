package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.StatisticsInterval;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.balazs.hajdu.repository.MeasurementResultRepository;
import com.google.common.collect.ImmutableList;
import org.bson.types.ObjectId;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.testng.Assert.*;

/**
 * @author Balazs Hajdu
 */
public class StatisticsServiceTest {

    private static final String TEST_USERNAME = "test-username";
    private static final String TEST_SENSOR_NAME = "test-sensor-name";
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();
    private static final LocalDateTime TEST_PREVIOUS_DATE = LocalDateTime.now().minusDays(1);
    private static final ObjectId TEST_ID = new ObjectId();
    private static final double TEST_X = 1;
    private static final double TEST_Y = 2;
    private static final double TEST_VALUE_TEN = 10;
    private static final double TEST_VALUE_FOUR = 4;
    private static final double TEST_VALUE_TWO = 2;
    private static final double TEST_VALUE_SIX = 6.0;
    private static final double TEST_ROUNDED_AVERAGE = 5.33;
    private static final double TEST_LONGITUDE = 21.02;
    private static final double TEST_LATITUDE = 45.21;
    private static final String TEST_FORMATTED_LOCATION = "test-formatted-location";

    @Mock
    private MeasurementResultRepository measurementResultRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnEmptyStatisticsIfNoResultWasFoundInDateRange() {
        // given
        given(measurementResultRepository.getMeasurementResultsBetweenDateRange(any(MeasurementResultQueryContext.class))).willReturn(Collections.emptyList());

        // when
        Optional<MeasurementResultStatistics> actual = statisticsService.getStatistics(TEST_USERNAME, TEST_SENSOR_NAME, StatisticsInterval.DAY);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    public void shouldReturnMeasurementResultStatisticsIfDataAvaialableForTheGivenDateRange() {
        // given
        given(measurementResultRepository.getMeasurementResultsBetweenDateRange(any(MeasurementResultQueryContext.class))).willReturn(measurementResults());

        // when
        Optional<MeasurementResultStatistics> actual = statisticsService.getStatistics(TEST_USERNAME, TEST_SENSOR_NAME, StatisticsInterval.DAY);

        // then
        assertTrue(actual.isPresent());
        assertThat(actual.get().getAverage(), is(TEST_VALUE_SIX));
        assertThat(actual.get().getMax(), is(TEST_VALUE_TEN));
        assertThat(actual.get().getMin(), is(TEST_VALUE_TWO));
    }

    @Test
    public void shouldRoundNumbersToTwoDigits() {
        // given
        given(measurementResultRepository.getMeasurementResultsBetweenDateRange(any(MeasurementResultQueryContext.class))).willReturn(measurementResultsWithIrrationalAverage());

        // when
        Optional<MeasurementResultStatistics> actual = statisticsService.getStatistics(TEST_USERNAME, TEST_SENSOR_NAME, StatisticsInterval.WEEK);

        // then
        assertTrue(actual.isPresent());
        assertThat(actual.get().getAverage(), is(TEST_ROUNDED_AVERAGE));
        assertThat(actual.get().getMin(), is(TEST_VALUE_TWO));
        assertThat(actual.get().getMax(), is(TEST_VALUE_TEN));
    }

    @Test
    public void shouldReturnStatisticsForLocation() {
        // given
        Coordinates coordinates = aCoordinates();
        GeocodedLocation geocodedLocation = aGeocodedLocation();
        List<MeasurementResult> measurementResults = ImmutableList.of(
                aMeasurementResult(LocalDateTime.now().minusDays(1), TEST_VALUE_TEN),
                aMeasurementResult(LocalDateTime.now(), TEST_VALUE_TWO),
                aMeasurementResult(LocalDateTime.now().plusDays(1), TEST_VALUE_FOUR));
        given(measurementResultRepository.getMeasurementResultsFromLocation(coordinates)).willReturn(measurementResults);

        // when
        Optional<MeasurementResultStatistics> actual = statisticsService.getStatisticsForLocation(geocodedLocation);

        // then
        assertTrue(actual.isPresent());
        assertThat(actual.get().getAverage(), is(TEST_ROUNDED_AVERAGE));
        assertThat(actual.get().getMin(), is(TEST_VALUE_TWO));
        assertThat(actual.get().getMax(), is(TEST_VALUE_TEN));
    }

    @Test
    public void shouldReturnEmptyWhenMeasurementResultIsUnavailable() {
        // given
        Coordinates coordinates = aCoordinates();
        GeocodedLocation geocodedLocation = aGeocodedLocation();
        List<MeasurementResult> measurementResults = ImmutableList.of(
                aMeasurementResult(LocalDateTime.now().minusDays(1), TEST_VALUE_TEN),
                aMeasurementResult(LocalDateTime.now(), TEST_VALUE_TWO),
                aMeasurementResult(LocalDateTime.now().plusDays(1), TEST_VALUE_FOUR));
        given(measurementResultRepository.getMeasurementResultsFromLocation(coordinates)).willReturn(Collections.emptyList());

        // when
        Optional<MeasurementResultStatistics> actual = statisticsService.getStatisticsForLocation(geocodedLocation);

        // then
        assertFalse(actual.isPresent());
    }

    private GeocodedLocation aGeocodedLocation() {
        return new GeocodedLocation.Builder()
                .withFormattedLocation(TEST_FORMATTED_LOCATION)
                .withCoordinates(aCoordinates())
                .build();
    }

    private Coordinates aCoordinates() {
        return new Coordinates.Builder()
                .withLongitude(TEST_LONGITUDE)
                .withLattitude(TEST_LATITUDE)
                .build();
    }

    private List<MeasurementResult> measurementResultsWithIrrationalAverage() {
        return ImmutableList.of(aMeasurementResult(TEST_DATE, TEST_VALUE_TEN),
                aMeasurementResult(TEST_PREVIOUS_DATE, TEST_VALUE_FOUR),
                aMeasurementResult(TEST_DATE, TEST_VALUE_TWO));
    }

    private List<MeasurementResult> measurementResults() {
        return ImmutableList.of(aMeasurementResult(TEST_DATE, TEST_VALUE_TEN),
                aMeasurementResult(TEST_PREVIOUS_DATE, TEST_VALUE_TWO));
    }

    private MeasurementResult aMeasurementResult(LocalDateTime localDateTime, double value) {
        return new MeasurementResult.Builder()
                .withDate(localDateTime)
                .withId(TEST_ID)
                .withLocation(TEST_X, TEST_Y)
                .withSensorName(TEST_SENSOR_NAME)
                .withUsername(TEST_USERNAME)
                .withValue(value)
                .build();
    }

}