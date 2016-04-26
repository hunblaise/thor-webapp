package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.google.common.collect.ImmutableList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class MeasurementResultTransformerTest {

    private static final double TEST_X_COORD = 26;
    private static final double TEST_Y_COORD = 13;
    private static final ObjectId TEST_OBJECT_ID = new ObjectId();
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();
    private static final double TEST_VALUE = 1;
    private static final String TEST_SENSOR_NAME = "test-sensor-name";
    private static final String TEST_USERNAME = "test-username";
    private static final GeoJsonPoint TEST_LOCATION = new GeoJsonPoint(TEST_X_COORD, TEST_Y_COORD);

    private MeasurementResultTransformer measurementResultTransformer;

    @BeforeMethod
    public void setUp() throws Exception {
        measurementResultTransformer = new MeasurementResultTransformer();
    }

    @Test
    public void shouldTransformThorRelatedObjectToDatabaseRelatedObject() {
        // given
        MeasurementResultEntity expected = aMeasurementResultEntity();

        // when
        MeasurementResultEntity actual = measurementResultTransformer.transform(aMeasurementResult());

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldTransformEntitiesToResponse() {
        // given
        List<MeasurementResponse> expected = ImmutableList.of(aMeasurementResponse());

        // when
        List<MeasurementResponse> actual = measurementResultTransformer.transformToResponse(ImmutableList.of(aMeasurementResultEntity()));

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldTransformDomainObjectToResponse() {
        // given
        List<MeasurementResponse> expected = ImmutableList.of(aMeasurementResponse());

        // when
        List<MeasurementResponse> actual = measurementResultTransformer.transformThorToResponse(ImmutableList.of(aMeasurementResult()));

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldTransformEntitiesToDomainObject() {
        // given
        List<MeasurementResult> expected = ImmutableList.of(aMeasurementResult());

        // when
        List<MeasurementResult> actual = measurementResultTransformer.transformToThor(ImmutableList.of(aMeasurementResultEntity()));

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldTransformDatabaseEntityIntoDomainObject() {
        // given
        MeasurementResult expected = aMeasurementResult();

        // when
        MeasurementResult actual = measurementResultTransformer.transformToThor(aMeasurementResultEntity());

        // then
        assertThat(actual, is(expected));
    }

    private MeasurementResponse aMeasurementResponse() {
        return new MeasurementResponse.Builder().withDate(TEST_DATE.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .withValue(TEST_VALUE)
                .build();
    }

    private MeasurementResult aMeasurementResult() {
        return new MeasurementResult.Builder().withId(TEST_OBJECT_ID)
                .withSensorName(TEST_SENSOR_NAME)
                .withUsername(TEST_USERNAME)
                .withDate(TEST_DATE)
                .withLocation(TEST_LOCATION.getX(), TEST_LOCATION.getY())
                .withValue(TEST_VALUE)
                .build();

    }

    private MeasurementResultEntity aMeasurementResultEntity() {
        MeasurementResultEntity measurementResultEntity = new MeasurementResultEntity();

        measurementResultEntity.setId(TEST_OBJECT_ID);
        measurementResultEntity.setDate(TEST_DATE);
        measurementResultEntity.setValue(TEST_VALUE);
        measurementResultEntity.setSensorName(TEST_SENSOR_NAME);
        measurementResultEntity.setUsername(TEST_USERNAME);
        measurementResultEntity.setLocation(TEST_LOCATION);

        return measurementResultEntity;
    }
}