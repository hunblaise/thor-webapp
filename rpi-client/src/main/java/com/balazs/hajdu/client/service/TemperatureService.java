package com.balazs.hajdu.client.service;

/**
 * An interface to read temperature values from the sensor.
 *
 * @author Balazs Hajdu
 */
public interface TemperatureService {

    /**
     * Read temperature value.
     *
     * @return temperature
     */
    double calculateTemperature();

}
