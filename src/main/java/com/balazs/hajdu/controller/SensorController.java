package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.SensorRequestForm;
import com.balazs.hajdu.repository.UserRepository;
import com.balazs.hajdu.service.SensorService;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * A controller to communicate with the sensor anf handle sensor related requests.
 *
 * @author Balazs Hajdu
 */
@RestController
public class SensorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Inject
    private SensorService sensorService;

    @RequestMapping(value = "/sensors", method = RequestMethod.GET)
    public ModelAndView listSensors(Principal principal,
                                    ModelAndView modelAndView,
                                    HttpServletRequest request) {
        if (principal != null) {
            modelAndView.addObject("sensors", sensorService.getAllSensorByUsername(principal.getName()));
        }

        modelAndView.setViewName(ViewNames.SENSORS.getValue());

        return modelAndView;
    }

    @RequestMapping(value = "/{username}/sensors/save", method = RequestMethod.POST)
    public ResponseEntity<Sensor> saveSensor(@PathVariable String username,
                                             @RequestBody SensorRequestForm sensorRequestForm) {

        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Sensor sensor = sensorService.saveSensor(username, sensorRequestForm);

        LOGGER.debug("Saved a new sensor for {} user: {}", username, sensor);

        return new ResponseEntity<>(sensor, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{username}/sensors/{sensorName}/save", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResult> addMeasurementValue(@PathVariable String sensorName,
                                                                 @PathVariable String username,
                                                                 @RequestBody MeasurementResultRequestForm measurementResult) {

        MeasurementResult result = sensorService.saveMeasurementResult(username, sensorName, measurementResult);

        LOGGER.debug("Saved a new measurement value for {} user from {} sensor: {}", username, sensorName, result);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
