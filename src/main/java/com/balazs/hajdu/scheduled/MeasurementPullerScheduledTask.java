package com.balazs.hajdu.scheduled;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.repository.ClientRepository;
import com.balazs.hajdu.repository.MeasurementResultRepository;
import com.balazs.hajdu.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Scheduled task.
 * Runs every hour and collect measurement data from the sensors.
 *
 * @author Balazs Hajdu
 */
@Component
public class MeasurementPullerScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementPullerScheduledTask.class);

    private static final String RETRIEVED_MEASUREMENT_RESULT = "{} user retrieved measurement result from {} sensor: {}";

    @Inject
    private UserRepository userRepository;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private MeasurementResultRepository measurementResultRepository;

    @Scheduled(fixedRate = 30000)
    public void pullMeasurementResults() {

        UserEntity userEntity = userRepository.findOneByUsername("thor");
        for (SensorEntity sensor : userEntity.getSensors()) {
            MeasurementResult measurementResult = clientRepository.getMeasurementResultFromClient(userEntity.getUsername(),
                    userEntity.getPassword(), sensor);
            LOGGER.debug(RETRIEVED_MEASUREMENT_RESULT, userEntity.getUsername(), sensor.getName(), measurementResult);

            measurementResultRepository.saveMeasurementResultToSensor(measurementResult);
        }
    }

}
