package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.context.UpdateSensorAlertContext;
import com.balazs.hajdu.domain.repository.maps.Coordinates;

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
     * Find users near a given coordinates.
     *
     * @param coordinates coordinates
     * @param radius
     * @return users
     */
    List<User> findUsersNearLocation(Coordinates coordinates, int radius);

    /**
     * Find users by full text search.
     *
     * @param keyword keyword
     * @return users
     */
    List<User> findUsersByFullTextSearch(String keyword);

}
