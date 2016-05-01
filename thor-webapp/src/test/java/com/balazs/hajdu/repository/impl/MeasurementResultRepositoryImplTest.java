package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.google.common.collect.ImmutableList;
import org.bson.types.ObjectId;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Query;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Balazs Hajdu
 */
public class MeasurementResultRepositoryImplTest {

    private static final LocalDateTime TEST_DATE = LocalDateTime.now();
    private static final String TEST_USERNAME = "test-username";
    private static final double TEST_LATITUDE = 45.21;
    private static final double TEST_LONGITUDE = 25.21;
    private static final double TEST_MEASUREMENT_VALUE = 32.1;
    private static final String TEST_SENSOR_NAME = "test-sensor-name";
    private static final ObjectId TEST_MEASUREMENT_ID = new ObjectId();

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MeasurementResultTransformer measurementResultTransformer;

    @InjectMocks
    private MeasurementResultRepositoryImpl measurementResultRepository;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetMeasurementResultsBetweenDateRange() {
        // given
        List<MeasurementResultEntity> measurementResultEntities = ImmutableList.of(aMeasurementResultEntity());
        given(mongoTemplate.find(any(Query.class), any(MeasurementResultEntity.class.getClass()))).willReturn(measurementResultEntities);
        // and
        List<MeasurementResult> measurementResults = ImmutableList.of(aMeasurementResult());
        given(measurementResultTransformer.transformToThor(measurementResultEntities)).willReturn(measurementResults);
        // and
        MeasurementResultQueryContext queryContext = aMeasurementResultQueryContext();

        // when
        List<MeasurementResult> actual = measurementResultRepository.getMeasurementResultsBetweenDateRange(queryContext);

        // then
        assertThat(actual, is(measurementResults));
    }

    @Test
    public void shouldSaveMeasurementResultToSensor() {
        // given
        MeasurementResult measurementResult = aMeasurementResult();
        MeasurementResultEntity measurementResultEntity = aMeasurementResultEntity();
        given(measurementResultTransformer.transform(measurementResult)).willReturn(measurementResultEntity);

        // when
        measurementResultRepository.saveMeasurementResultToSensor(measurementResult);

        // then
        verify(mongoTemplate, times(1)).insert(measurementResultEntity);
    }

    @Test
    public void shouldGetMeasurementResultsFromLocation() {
        // given
        Coordinates coordinates = aCoordinates();
        List<MeasurementResultEntity> measurementResultEntities = ImmutableList.of(aMeasurementResultEntity());
        given(mongoTemplate.find(any(Query.class), any(MeasurementResultEntity.class.getClass()))).willReturn(measurementResultEntities);
        // and
        List<MeasurementResult> measurementResults = ImmutableList.of(aMeasurementResult());
        given(measurementResultTransformer.transformToThor(measurementResultEntities)).willReturn(measurementResults);

        // when
        List<MeasurementResult> actual = measurementResultRepository.getMeasurementResultsFromLocation(coordinates);

        // then
        assertThat(actual, is(measurementResults));
    }

    private Coordinates aCoordinates() {
        return new Coordinates.Builder()
                .withLattitude(TEST_LATITUDE)
                .withLongitude(TEST_LONGITUDE)
                .build();
    }

    private MeasurementResultQueryContext aMeasurementResultQueryContext() {
        return new MeasurementResultQueryContext.Builder()
                .withUsername(TEST_USERNAME)
                .withEndDate(LocalDateTime.now())
                .withStartDate(LocalDateTime.now().minusDays(1))
                .withSensorName(TEST_SENSOR_NAME)
                .build();
    }

    private MeasurementResult aMeasurementResult() {
        return new MeasurementResult.Builder()
                .withDate(TEST_DATE)
                .withSensorName(TEST_SENSOR_NAME)
                .withUsername(TEST_USERNAME)
                .withLocation(TEST_LATITUDE, TEST_LONGITUDE)
                .withValue(TEST_MEASUREMENT_VALUE)
                .withId(TEST_MEASUREMENT_ID)
                .build();
    }

    private MeasurementResultEntity aMeasurementResultEntity() {
        MeasurementResultEntity measurementResultEntity = new MeasurementResultEntity();

        measurementResultEntity.setDate(TEST_DATE);
        measurementResultEntity.setUsername(TEST_USERNAME);
        measurementResultEntity.setLocation(new GeoJsonPoint(TEST_LATITUDE, TEST_LONGITUDE));
        measurementResultEntity.setValue(TEST_MEASUREMENT_VALUE);
        measurementResultEntity.setSensorName(TEST_SENSOR_NAME);
        measurementResultEntity.setId(TEST_MEASUREMENT_ID);

        return measurementResultEntity;
    }
}