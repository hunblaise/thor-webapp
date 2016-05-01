package com.balazs.hajdu.client.delegator;

import com.balazs.hajdu.client.domain.config.SensorTypes;
import com.balazs.hajdu.client.domain.response.TemperatureSensorResponse;
import com.balazs.hajdu.client.service.DataConversionService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Sensor delegator.
 *
 * @author Balazs Hajdu
 */
@Component
public class SensorDelegator {

    @Inject
    private DataConversionService dataConversionService;

    public double delegate(String sensorName) {
        double sensorValue;
        TemperatureSensorResponse temperatureSensorResponse = dataConversionService.convertData();
        SensorTypes sensorTypes = SensorTypes.getSensorTypeByAlias(sensorName);
        if (sensorTypes != null) {
            switch (sensorTypes) {
                case TEMPERATURE:
                    sensorValue = temperatureSensorResponse.getTemperature().getValue();
                    break;
                case PRESSURE:
                    sensorValue = temperatureSensorResponse.getPressure().getValue();
                    break;
                default:
                    sensorValue = 0;
                    break;
            }
        } else {
            throw new IllegalArgumentException("Invalid sensor name.");
        }

        return sensorValue;
    }
}
