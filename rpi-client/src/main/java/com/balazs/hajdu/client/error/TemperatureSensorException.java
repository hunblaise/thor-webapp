package com.balazs.hajdu.client.error;

/**
 * Encapsulates calibration related issues.
 *
 * @author Balazs Hajdu
 */
public class TemperatureSensorException extends Exception {

    public TemperatureSensorException(String message) {
        super(message);
    }

    public TemperatureSensorException(String message, Exception cause) {
        super(message, cause);
    }

}
