package com.balazs.hajdu.client.repository;

/**
 * An interface to read pressure information from the sensor.
 *
 * @author Balazs Hajdu
 */
public interface PressureRepository {

    /**
     * A method to read pressure data.
     *
     * @return uncompensated pressure data
     */
    int readPressure();

}
