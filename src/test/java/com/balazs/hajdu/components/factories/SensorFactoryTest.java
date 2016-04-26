package com.balazs.hajdu.components.factories;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.StatisticsInterval;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.google.common.collect.ImmutableList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class SensorFactoryTest {

    private static final ObjectId TEST_OBJECT_ID = new ObjectId();
    private static final String TEST_SENSOR_NAME = "test-sensor-name";
    private static final double TEST_MAX_ALERT = 23;
    private static final double TEST_MIN_ALERT = 10;
    private static final double TEST_X_COORD = 35;
    private static final double TEST_Y_COORD = 6;
    private static final GeoJsonPoint TEST_LOCATION = new GeoJsonPoint(TEST_X_COORD, TEST_Y_COORD);
    private static final String TEST_USERNAME = "test-username";
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();
    private static final double TEST_VALUE = 345;
    private static final double TEST_AVERAGE = 23.45;
    private static final double TEST_MAX = 34;
    private static final double TEST_MIN = 12;

    private SensorFactory sensorFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        sensorFactory = new SensorFactory();
    }

    @Test
    public void shouldCreateDatabaseEntityFromDomainObject() {
        // given
        SensorEntity expected = aSensorEntity();

        // when
        SensorEntity actual = sensorFactory.transform(aSensor());

        // then
        assertThat(actual.getLocation(), is(expected.getLocation()));
        assertThat(actual.getMaxAlert(), is(expected.getMaxAlert()));
        assertThat(actual.getMinAlert(), is(expected.getMinAlert()));
        assertThat(actual.getName(), is(expected.getName()));
    }

    @Test
    public void shouldCreateSensorDomainObject() {
        // given
        List<Sensor> expected = ImmutableList.of(aSensor());
        // and
        Map<String, List<MeasurementResult>> measurementResults = new HashMap<>(1);
        measurementResults.put(TEST_SENSOR_NAME, ImmutableList.of(aMeasurementResult()));
        // and
        Map<String, Map<StatisticsInterval, MeasurementResultStatistics>> statistics = new HashMap<>(1);
        statistics.put(TEST_SENSOR_NAME, aMeasurementResultsStatistics());

        // when
        List<Sensor> actual = sensorFactory.transform(ImmutableList.of(aSensorEntity()), measurementResults, statistics);

        // then
        assertThat(actual, is(expected));
    }

    private Sensor aSensor() {
        return new Sensor.Builder().withId(TEST_OBJECT_ID)
                .withSensorName(TEST_SENSOR_NAME)
                .withLocation(TEST_X_COORD, TEST_Y_COORD)
                .withMaxAlert(TEST_MAX_ALERT)
                .withMinAlert(TEST_MIN_ALERT)
                .withMeasurementResults(ImmutableList.of(aMeasurementResult()))
                .withMeasurementResultStatistics(aMeasurementResultsStatistics())
                .build();
    }

    private Map<StatisticsInterval, MeasurementResultStatistics> aMeasurementResultsStatistics() {
        Map<StatisticsInterval, MeasurementResultStatistics> measurementResultStatisticsMap = new EnumMap<>(StatisticsInterval.class);

        measurementResultStatisticsMap.put(StatisticsInterval.WEEK, new MeasurementResultStatistics.Builder()
                .withAverage(TEST_AVERAGE)
                .withMax(TEST_MAX).withMin(TEST_MIN)
                .build());

        return measurementResultStatisticsMap;
    }

    private MeasurementResult aMeasurementResult() {
        return new MeasurementResult.Builder().withSensorName(TEST_SENSOR_NAME)
                .withUsername(TEST_USERNAME)
                .withLocation(TEST_X_COORD, TEST_Y_COORD)
                .withId(TEST_OBJECT_ID)
                .withDate(TEST_DATE)
                .withValue(TEST_VALUE)
                .build();
    }

    private SensorEntity aSensorEntity() {
        SensorEntity sensorEntity = new SensorEntity();

        sensorEntity.setId(TEST_OBJECT_ID);
        sensorEntity.setName(TEST_SENSOR_NAME);
        sensorEntity.setMaxAlert(TEST_MAX_ALERT);
        sensorEntity.setMinAlert(TEST_MIN_ALERT);
        sensorEntity.setLocation(TEST_LOCATION);

        return sensorEntity;
    }
}