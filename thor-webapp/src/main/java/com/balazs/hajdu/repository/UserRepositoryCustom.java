package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.context.UpdateSensorAlertContext;
import com.balazs.hajdu.domain.repository.maps.Location;

import java.util.List;

/**
 * Update sensor related data in the database.
 *
 * @author Balazs Hajdu
 */
public interface UserRepositoryCustom {

    /**
     * Save a new sensor to the given user.
     *
     * @param username username
     * @param sensor sensor
     */
    void saveSensorToUser(String username, Sensor sensor);

    /**
     * Update sensor's alert values.
     *
     * @param context update context
     */
    void updateSensorAlertValues(UpdateSensorAlertContext context);

    /**
     * Find users near a given location.
     *
     * @param location location
     * @return users
     */
    List<User> findUsersNearLocation(Location location);

}
