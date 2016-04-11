package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.components.transformers.SensorFactory;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.repository.UserRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.inject.Inject;

import java.util.Optional;

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
    private static final String SENSORNAME_FIELD = "sensors.name";
    private static final String MEASUREMENT_RESULTS_FIELD = "sensors.measurementResults";

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private MeasurementResultTransformer measurementResultTransformer;

    @Inject
    private SensorFactory sensorFactory;

    @Override
    public void saveSensorToUser(String username, Optional<Sensor> sensor) {

        if (sensor.isPresent()) {
            SensorEntity sensorEntity = sensorFactory.transform(sensor.get());

            mongoTemplate.updateFirst(new Query(where(USERNAME_FIELD).is(username)),
                    new Update().push(SENSORS_FIELD, sensorEntity),
                    SensorEntity.class);

            LOGGER.debug("Added a new sensor to user {}: {}", username, sensor);
        } else {
            LOGGER.error("Could not save empty sensor.");
            throw new IllegalArgumentException("Could not save empty sensor.");
        }

    }

    @Override
    public void saveMeasurementResultToSensor(String username, String sensorName, MeasurementResult measurementResult) {
        MeasurementResultEntity measurementResultEntity = measurementResultTransformer.transform(measurementResult);

        mongoTemplate.insert(measurementResultEntity);

        LOGGER.debug("Saved to the database a new measurement result: {}", measurementResultEntity);
    }

}
