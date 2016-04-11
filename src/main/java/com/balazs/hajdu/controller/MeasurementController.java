package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.security.Principal;

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

}
