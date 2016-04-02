package com.balazs.hajdu.controller;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.SensorRequestForm;
import com.balazs.hajdu.repository.UserRepository;
import com.balazs.hajdu.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class SensorController {

    @Inject
    private SensorService sensorService;

    @RequestMapping(value = "/{username}/sensors/save", method = RequestMethod.POST)
    public ResponseEntity<Sensor> saveSensor(@PathVariable String username,
                                             @RequestBody SensorRequestForm sensorRequestForm) {

        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Sensor sensor = sensorService.saveSensor(username, sensorRequestForm);

        return new ResponseEntity<Sensor>(sensor, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userName}/sensors/{sensorName}/save", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResult> addMeasurementValue(@PathVariable String sensorName,
                                                                 @PathVariable String userName,
                                                                 @RequestBody MeasurementResultRequestForm measurementResult) {

        MeasurementResult result = sensorService.saveMeasurementResult(userName, sensorName, measurementResult);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
