package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.Endpoints;
import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.SensorRequestForm;
import com.balazs.hajdu.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

/**
 * A controller to communicate with the sensor and handle sensor related requests.
 *
 * @author Balazs Hajdu
 */
@RestController
public class SensorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private static final String REDIRECT_PREFIX = "redirect:";

    @Inject
    private SensorService sensorService;

    @RequestMapping(value = "/sensors", method = RequestMethod.GET)
    public ModelAndView listSensors(Principal principal,
                                    ModelAndView modelAndView) {
        if (principal != null) {
            modelAndView.addObject("sensors", sensorService.getAllSensorByUsername(principal.getName()));
        }

        modelAndView.setViewName(ViewNames.SENSORS.getValue());

        return modelAndView;
    }

    @RequestMapping(value = "/{username}/sensors/save", method = RequestMethod.POST)
    public ModelAndView saveSensor(@PathVariable String username,
                                             SensorRequestForm sensorRequestForm,
                                             BindingResult bindingResult,
                                             ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            Optional<Sensor> sensor = sensorService.saveSensor(username, sensorRequestForm);
            LOGGER.debug("Saved a new sensor for {} user: {}", username, sensor);
        }

        modelAndView.setViewName(REDIRECT_PREFIX + Endpoints.SENSORS.getRelativeEndpoint());

        return modelAndView;
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
