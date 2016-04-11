package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;

import java.util.Optional;

/**
 * Update sensor related data in the database.
 *
 * @author Balazs Hajdu
 */
public interface UserRepositoryCustom {

    void saveSensorToUser(String username, Optional<Sensor> sensor);

    void saveMeasurementResultToSensor(String userName, String sensorName, MeasurementResult measurementResult);

}
