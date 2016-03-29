package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.MeasurementResult;

/**
 * Update sensor related data in the database.
 *
 * @author Balazs Hajdu
 */
public interface UserRepositoryCustom {

    void addNewMeasurement(String username, String sensorName, MeasurementResult measurementResult);

}
