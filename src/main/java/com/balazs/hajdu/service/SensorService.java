package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.context.UpdateSensorAlertContext;
import com.balazs.hajdu.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service to handle the measurement results.
 *
 * @author Balazs Hajdu
 */
@Service
public class SensorService {

    @Inject
    private UserRepository userRepository;

    /**
     * Save a new sensor for the given user.
     *
     * @param username username
     * @param sensor sensor
     * @return saved sensor
     */
    public Sensor saveSensor(String username, Sensor sensor) {

        userRepository.saveSensorToUser(username, sensor);

        return sensor;
    }

    /**
     * Lists all of the sensors for the given user.
     *
     * @param username username
     * @return list of sensors
     */
    public List<String> getSensorNames(String username) {
        return userRepository.findOneByUsername(username).getSensors().stream()
                .map(sensorEntity -> sensorEntity.getName())
                .collect(Collectors.toList());
    }

    /**
     * Update sensor's alert values.
     *
     * @param updateSensorAlertContext update context
     */
    public void updateAlertValueForSensor(UpdateSensorAlertContext updateSensorAlertContext) {
        userRepository.updateSensorAlertValues(updateSensorAlertContext);
    }
}
