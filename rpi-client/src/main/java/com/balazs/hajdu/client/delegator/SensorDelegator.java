package com.balazs.hajdu.client.delegator;

import com.balazs.hajdu.client.domain.config.SensorTypes;
import com.balazs.hajdu.client.repository.TemperatureRepository;
import com.balazs.hajdu.client.service.TemperatureService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Balazs Hajdu
 */
@Component
public class SensorDelegator {

    @Inject
    private TemperatureService temperatureService;

    public double delegate(String sensorName) {
        double sensorValue;

        switch (SensorTypes.getSensorTypeByAlias(sensorName)) {
            case TEMPERATURE:
                sensorValue = temperatureService.calculateTemperature();
                break;
            case HUMIDITY:
                sensorValue = 0;
                break;
            default:
                sensorValue = 0;
                break;
        }

        return sensorValue;
    }
}
