package com.balazs.hajdu.facade;

import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.view.SensorAlertRequestForm;
import com.balazs.hajdu.domain.view.SensorRequestForm;
import com.balazs.hajdu.error.exceptions.InvalidDatabaseOperationException;

import java.util.List;

/**
 * Sensor facade.
 *
 * @author Balazs Hajdu
 */
public interface SensorFacade {

    /**
     * Retrieve all of the available sensor for the given user.
     *
     * @param username username
     * @return available sensors
     */
    List<Sensor> getAllSensorByUsername(String username);

    /**
     * Save a new sensor for the given user.
     *
     * @param username username
     * @param sensorRequestForm request form
     * @return saved sensor
     */
    Sensor saveSensor(String username, SensorRequestForm sensorRequestForm) throws InvalidDatabaseOperationException;

    /**
     * Modify sensor alert values for the given sensor
     *
     * @param username
     * @param sensorAlertRequestForm
     * @return
     */
    void updateSensorAlerts(String username, SensorAlertRequestForm sensorAlertRequestForm);

}
