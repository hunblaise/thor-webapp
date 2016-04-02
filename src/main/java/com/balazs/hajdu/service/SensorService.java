package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.SensorRequestForm;
import com.balazs.hajdu.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * A service to handle the measurement results.
 *
 * @author Balazs Hajdu
 */
@Service
public class SensorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

    @Inject
    private UserRepository userRepository;

    public Sensor saveSensor(String username, SensorRequestForm sensorRequestForm) {

        Sensor sensor = new Sensor.Builder().withSensorName(sensorRequestForm.getSensorName())
                .withLocation(sensorRequestForm.getLat(), sensorRequestForm.getLon())
                .withMeasurementResults(Collections.emptyList())
                .build();

        userRepository.saveSensorToUser(username, sensor);

        return sensor;
    }

    /**
     * Save a measurment result.
     *
     * @param userName username
     * @param sensorName sensor's name
     * @param requestForm measurment result
     * @return saved measurment result
     */
    public MeasurementResult saveMeasurementResult(String userName, String sensorName, MeasurementResultRequestForm requestForm) {

        MeasurementResult result = new MeasurementResult.Builder().withValue(requestForm.getValue())
                .withLocation(requestForm.getLat(), requestForm.getLon())
                .withDate(LocalDateTime.now())
                .build();

        userRepository.saveMeasurementResultToSensor(userName, sensorName, result);

        return result;
    }

}
