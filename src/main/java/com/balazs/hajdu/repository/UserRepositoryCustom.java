package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.Sensor;

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

}
