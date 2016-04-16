package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.components.transformers.SensorFactory;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.repository.UserRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.inject.Inject;

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

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private SensorFactory sensorFactory;

    @Override
    public void saveSensorToUser(String username, Sensor sensor) {

        SensorEntity sensorEntity = sensorFactory.transform(sensor);

        mongoTemplate.updateFirst(new Query(where(USERNAME_FIELD).is(username)),
                new Update().push(SENSORS_FIELD, sensorEntity),
                SensorEntity.class);

        LOGGER.debug("Added a new sensor to user {}: {}", username, sensor);
    }

}
