package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Measurement result related repository.
 *
 * @author Balazs Hajdu
 */
public interface MeasurementResultRepository extends MongoRepository<MeasurementResultEntity, ObjectId> {

    List<MeasurementResultEntity> findByUsernameAndSensorNameAllIgnoreCase(String username, String sensorName);

}
