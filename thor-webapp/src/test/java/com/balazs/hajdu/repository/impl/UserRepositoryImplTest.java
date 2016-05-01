package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.components.factories.SensorFactory;
import com.balazs.hajdu.components.transformers.UserTransformer;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.UserRoles;
import com.balazs.hajdu.domain.context.UpdateSensorAlertContext;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.google.common.collect.ImmutableList;
import org.bson.types.ObjectId;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Collections;
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
public class UserRepositoryImplTest {

    private static final double TEST_MINIMUM_VALUE_ALERT = 10;
    private static final double TEST_MAXIMUM_VALUE_ALERT = 30;
    private static final String TEST_SENSOR_NAME = "test-sensor-name";
    private static final ObjectId TEST_SENSOR_ID = new ObjectId();
    private static final double TEST_LOCATION_LATITUDE = 46.10;
    private static final double TEST_LOCATION_LONGITUDE = 21.32;
    private static final String TEST_USERNAME = "test-username";
    private static final String TEST_USER_ADDRESS = "test-user-address";
    private static final String TEST_PASSWORD = "test-password";
    private static final String TEST_USER_ROLE = "test-user-role";
    private static final LocalDateTime TEST_DATE = LocalDateTime.now();
    private static final ObjectId TEST_USER_ID = new ObjectId();
    private static final int TEST_DISTANCE = 10;
    private static final String TEST_FORMATTED_ADDRESS = "test-formatted-location";
    private static final String TEST_FULL_TEXT = "test-full-text";

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private SensorFactory sensorFactory;

    @Mock
    private UserTransformer userTransformer;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveSensorToUser() {
        // given
        Sensor sensor = aSensor();
        SensorEntity sensorEntity = aSensorEntity();
        given(sensorFactory.transform(sensor)).willReturn(sensorEntity);

        // when
        userRepository.saveSensorToUser(TEST_USERNAME, sensor);

        // then
        verify(mongoTemplate, times(1)).updateFirst(any(Query.class), any(Update.class), any(UserEntity.class.getClass()));
    }

    @Test
    public void shouldUpdateSensorAlertValues() {
        // given
        UpdateSensorAlertContext sensorAlertContext = aSensorAlertContext();

        // when
        userRepository.updateSensorAlertValues(sensorAlertContext);

        // then
        verify(mongoTemplate, times(1)).updateFirst(any(Query.class), any(Update.class), any(UserEntity.class.getClass()));
    }

    @Test
    public void shouldFindUsersNearAGivenLocation() {
        // given
        Coordinates coordinates = aCoordinates();
        // and
        List<UserEntity> userEntities = ImmutableList.of(aUserEntity());
        given(mongoTemplate.find(any(Query.class), any(UserEntity.class.getClass()))).willReturn(userEntities);
        // and
        List<User> users = ImmutableList.of(aUser());
        given(userTransformer.transform(userEntities)).willReturn(users);

        // when
        List<User> actual = userRepository.findUsersNearLocation(coordinates, TEST_DISTANCE);

        // then
        assertThat(actual, is(users));
    }

    @Test
    public void shouldFindUsersByFullTextSearch() {
        // given
        List<UserEntity> userEntities = ImmutableList.of(aUserEntity());
        given(mongoTemplate.find(any(TextQuery.class), any(UserEntity.class.getClass()))).willReturn(userEntities);
        // and
        List<User> users = ImmutableList.of(aUser());
        given(userTransformer.transform(userEntities)).willReturn(users);

        // when
        List<User> actual = userRepository.findUsersByFullTextSearch(TEST_FULL_TEXT);

        // then
        assertThat(actual, is(users));
    }

    private User aUser() {
        return new User.Builder()
                .withUsername(TEST_USERNAME)
                .withUserRole(UserRoles.USER)
                .withPassword(TEST_PASSWORD)
                .withLocation(aGeocodedLocation())
                .build();
    }

    private GeocodedLocation aGeocodedLocation() {
        return new GeocodedLocation.Builder()
                .withCoordinates(aCoordinates())
                .withFormattedLocation(TEST_FORMATTED_ADDRESS)
                .build();
    }

    private UserEntity aUserEntity() {
        UserEntity userEntity = new UserEntity();

        userEntity.setAddress(TEST_USER_ADDRESS);
        userEntity.setPassword(TEST_PASSWORD);
        userEntity.setRole(TEST_USER_ROLE);
        userEntity.setLocation(new GeoJsonPoint(TEST_LOCATION_LATITUDE, TEST_LOCATION_LONGITUDE));
        userEntity.setSensors(Collections.emptyList());
        userEntity.setCreated(TEST_DATE);
        userEntity.setUsername(TEST_USERNAME);
        userEntity.setId(TEST_USER_ID);

        return userEntity;
    }

    private Coordinates aCoordinates() {
        return new Coordinates.Builder()
                .withLattitude(TEST_LOCATION_LATITUDE)
                .withLongitude(TEST_LOCATION_LONGITUDE)
                .build();
    }

    private UpdateSensorAlertContext aSensorAlertContext() {
        return new UpdateSensorAlertContext.Builder()
                .withMaxValue(TEST_MAXIMUM_VALUE_ALERT)
                .withMinValue(TEST_MINIMUM_VALUE_ALERT)
                .withSensorName(TEST_SENSOR_NAME)
                .withUsername(TEST_USERNAME)
                .build();
    }

    private SensorEntity aSensorEntity() {
        SensorEntity sensorEntity = new SensorEntity();

        sensorEntity.setLocation(new GeoJsonPoint(TEST_LOCATION_LATITUDE, TEST_LOCATION_LONGITUDE));
        sensorEntity.setMinAlert(TEST_MINIMUM_VALUE_ALERT);
        sensorEntity.setMaxAlert(TEST_MAXIMUM_VALUE_ALERT);
        sensorEntity.setName(TEST_SENSOR_NAME);

        return sensorEntity;
    }

    private Sensor aSensor() {
        return new Sensor.Builder()
                .withMeasurementResultStatistics(Collections.emptyMap())
                .withMeasurementResults(Collections.emptyList())
                .withMinAlert(TEST_MINIMUM_VALUE_ALERT)
                .withMaxAlert(TEST_MAXIMUM_VALUE_ALERT)
                .withSensorName(TEST_SENSOR_NAME)
                .withId(TEST_SENSOR_ID)
                .withLocation(TEST_LOCATION_LATITUDE, TEST_LOCATION_LONGITUDE)
                .build();
    }

}