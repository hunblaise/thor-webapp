package com.balazs.hajdu.client.repository;

import com.balazs.hajdu.client.domain.config.Bmp180Configuration;

/**
 * An interface to read temperature values from the sensor.
 *
 * @author Balazs Hajdu
 */
public interface TemperatureRepository {

    /**
     * A method to read temperature data.
     *
     * @return uncompensated temperature data
     */
    int readTemperature();

    /**
     * Get sensor configuration.
     *
     * @return sensor configuration
     */
    Bmp180Configuration getConfiguration();

}
