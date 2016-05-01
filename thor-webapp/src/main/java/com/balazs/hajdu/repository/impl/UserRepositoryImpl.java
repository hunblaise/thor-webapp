package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.components.factories.SensorFactory;
import com.balazs.hajdu.components.transformers.UserTransformer;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.context.UpdateSensorAlertContext;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.repository.UserRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Default implementation for {@link com.balazs.hajdu.repository.UserRepositoryCustom}.
 *
 * @author Balazs Hajdu
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private static final String USERNAME_FIELD = "username";
    private static final String SENSORS_FIELD = "sensors";
    private static final String USER_LOCATION_FIELD = "location";
    private static final String SENSORNAME_FIELD = "sensors.name";
    private static final String SENSOR_MAX_VALUE_ALERT = "sensors.$.maxAlert";
    private static final String SENSOR_MIN_VALUE_ALERT = "sensors.$.minAlert";

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private SensorFactory sensorFactory;

    @Inject
    private UserTransformer userTransformer;

    @Override
    public void saveSensorToUser(String username, Sensor sensor) {

        SensorEntity sensorEntity = sensorFactory.transform(sensor);

        mongoTemplate.updateFirst(new Query(where(USERNAME_FIELD).is(username)),
                new Update().push(SENSORS_FIELD, sensorEntity),
                SensorEntity.class);

        LOGGER.debug("Added a new sensor to user {}: {}", username, sensor);
    }

    @Override
    public void updateSensorAlertValues(UpdateSensorAlertContext context) {

        mongoTemplate.updateFirst(new Query(where(USERNAME_FIELD).is(context.getUsername())
                        .and(SENSORNAME_FIELD).is(context.getSensorName())),
                new Update().set(SENSOR_MAX_VALUE_ALERT, context.getMaxValue())
                        .set(SENSOR_MIN_VALUE_ALERT, context.getMinValue()),
                UserEntity.class);
    }

    @Override
    public List<User> findUsersNearLocation(Coordinates coordinates, int distance) {
        Point point = new Point(coordinates.getLat(), coordinates.getLon());

        Distance d = new Distance(distance, Metrics.KILOMETERS);
        List<UserEntity> userEntities = mongoTemplate.find(new Query(Criteria.where(USER_LOCATION_FIELD)
                .nearSphere(point)
                .maxDistance(d.getNormalizedValue())), UserEntity.class);

        return userTransformer.transform(userEntities);
    }

    @Override
    public List<User> findUsersByFullTextSearch(String keyword) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(keyword);

        List<UserEntity> userEntities = mongoTemplate.find(TextQuery.queryText(criteria).sortByScore(), UserEntity.class);

        return userTransformer.transform(userEntities);
    }

}
