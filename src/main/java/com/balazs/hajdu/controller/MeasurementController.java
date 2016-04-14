package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

/**
 * A controller to handle measurement results related operations.
 *
 * @author Balazs Hajdu
 */
@RestController
public class MeasurementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementController.class);

    @Inject
    private SensorService sensorService;

    @RequestMapping(value = "/measurments", method = RequestMethod.GET)
    public ModelAndView listMeasurementResults(Principal principal,
                                               ModelAndView modelAndView) {
        if (principal != null) {
            modelAndView.addObject("sensors", sensorService.getAllSensorByUsername(principal.getName()));
        }

        modelAndView.setViewName(ViewNames.MEASUREMENTS.getValue());

        return modelAndView;
    }

    @RequestMapping(value = "/{username}/sensors/get/names")
    public List<String> getSensorNames(@PathVariable String username) {
        return sensorService.getSensorNames(username);
    }

    @RequestMapping(value = "/{username}/sensors/{sensorName}/get/results", method = RequestMethod.GET)
    public List<MeasurementResponse> getMeasurementResults(@PathVariable String username,
                                                           @PathVariable String sensorName) {
        return sensorService.getAllMeasurementResultBySensorName(username, sensorName);
    }

}
