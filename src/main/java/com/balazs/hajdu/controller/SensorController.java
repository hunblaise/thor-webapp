package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.Endpoints;
import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.SensorAlertRequestForm;
import com.balazs.hajdu.domain.view.SensorRequestForm;
import com.balazs.hajdu.error.exceptions.InvalidDatabaseOperationException;
import com.balazs.hajdu.facade.MeasurementResultFacade;
import com.balazs.hajdu.facade.SensorFacade;
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
    private static final String SAVE_ERROR_MESSAGE = "Could not save new sensor into the database.";
    private static final String UPDATE_ERROR_MESSAGE = "Could not update the %s's alert values.";
    private static final String SUCCESS = "Success";

    @Inject
    private SensorFacade sensorFacade;

    @Inject
    private MeasurementResultFacade measurementResultFacade;

    @RequestMapping(value = "/sensors", method = RequestMethod.GET)
    public ModelAndView listSensors(Principal principal,
                                    ModelAndView modelAndView) {
        if (principal != null) {
            modelAndView.addObject("sensors", sensorFacade.getAllSensorByUsername(principal.getName()));
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
            try {
                sensorFacade.saveSensor(username, sensorRequestForm);
            } catch (InvalidDatabaseOperationException e) {
                LOGGER.error(SAVE_ERROR_MESSAGE, e);
                modelAndView.addObject("errors", SAVE_ERROR_MESSAGE);
            }
        }

        modelAndView.setViewName(REDIRECT_PREFIX + Endpoints.SENSORS.getRelativeEndpoint());

        return modelAndView;
    }

    @RequestMapping(value = "/{username}/sensors/{sensorName}/save", method = RequestMethod.POST)
    public ResponseEntity<MeasurementResult> addMeasurementValue(@PathVariable String sensorName,
                                                                 @PathVariable String username,
                                                                 @RequestBody MeasurementResultRequestForm measurementResult) {

        MeasurementResult result = measurementResultFacade.saveMeasurementResult(username, sensorName, measurementResult);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{username}/sensors/save/alert", method = RequestMethod.POST)
    public ModelAndView modifyAlertValues(@PathVariable String username,
                                          SensorAlertRequestForm sensorAlertRequestForm,
                                          BindingResult bindingResult,
                                          ModelAndView modelAndView) {

        if (!bindingResult.hasErrors()) {
            sensorFacade.updateSensorAlerts(username, sensorAlertRequestForm);
            modelAndView.addObject("success", SUCCESS);
        } else {
            modelAndView.addObject("error", String.format(UPDATE_ERROR_MESSAGE, sensorAlertRequestForm.getSensorAlert()));
        }

        modelAndView.setViewName(REDIRECT_PREFIX + Endpoints.SENSORS.getRelativeEndpoint());
        return modelAndView;
    }

}
