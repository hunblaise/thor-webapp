package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;

/**
 * Update sensor related data in the database.
 *
 * @author Balazs Hajdu
 */
public interface UserRepositoryCustom {

    void saveSensorToUser(String username, Sensor sensor);

    void saveMeasurementResultToSensor(String userName, String sensorName, MeasurementResult measurementResult);

}
