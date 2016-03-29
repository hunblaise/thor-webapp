package com.balazs.hajdu.controller;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * A controller to communicate with the sensor.
 *
 * @author Balazs Hajdu
 */
@RestController
@RequestMapping("/{userName}/sensors/{sensorName}/save")
public class SensorController {

    @Inject
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MeasurementResult> addMeasurementValue(@PathVariable String sensorName,
                                                                 @PathVariable String userName,
                                                                 @RequestBody MeasurementResultRequestForm measurementResult) {

        MeasurementResult result = new MeasurementResult.Builder().withValue(measurementResult.getValue())
                .withLocation(measurementResult.getLon(), measurementResult.getLat())
                .withDate(LocalDateTime.now())
                .build();

        userRepository.addNewMeasurement(userName, sensorName, result);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
