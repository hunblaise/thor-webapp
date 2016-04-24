package com.balazs.hajdu.client.repository;

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

}
