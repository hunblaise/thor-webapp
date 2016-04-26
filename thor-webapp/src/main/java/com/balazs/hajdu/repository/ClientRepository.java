package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.repository.SensorEntity;

/**
 * A repository the retrieve sensor related informations.
 *
 * @author Balazs Hajdu
 */
public interface ClientRepository {

    /**
     * Retrieves measurement results from the client.
     *
     * @param username username
     * @param password password
     * @param sensor sensor
     * @return measurement result
     */
    MeasurementResult getMeasurementResultFromClient(String username, String password, SensorEntity sensor);

}
