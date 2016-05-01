package com.balazs.hajdu.client.service;

import com.balazs.hajdu.client.domain.response.TemperatureSensorResponse;

/**
 * A service to convert uncompensated data into normal value.
 *
 * @author Balazs Hajdu
 */
public interface DataConversionService {

    /**
     * Convert uncompensated data into normal value.
     *
     * @return temperature sensor response
     */
    TemperatureSensorResponse convertData();

}
