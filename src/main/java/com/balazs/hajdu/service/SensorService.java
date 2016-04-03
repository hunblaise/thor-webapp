package com.balazs.hajdu.service;

import com.balazs.hajdu.components.transformers.SensorTransformer;
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
import java.util.List;

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

    @Inject
    private SensorTransformer sensorTransformer;

    /**
     * Retrieve all of the available sensor for the given user.
     *
     * @param username username
     * @return available sensors
     */
    public List<Sensor> getAllSensorByUsername(String username) {
        return sensorTransformer.transform(userRepository.findOneByUsername(username).getSensors());
    }

    /**
     * Adds a new sensor for the user.
     *
     * @param username username
     * @param sensorRequestForm request form
     * @return saved sensor
     */
    public Sensor saveSensor(String username, SensorRequestForm sensorRequestForm) {

        Sensor sensor = new Sensor.Builder().withSensorName(sensorRequestForm.getSensorName())
                .withLocation(sensorRequestForm.getLat(), sensorRequestForm.getLon())
                .withMeasurementResults(Collections.emptyList())
                .build();

        userRepository.saveSensorToUser(username, sensor);

        return sensor;
    }

    /**
     * Saves a measurement result.
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
