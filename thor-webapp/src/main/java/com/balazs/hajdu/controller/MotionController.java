package com.balazs.hajdu.controller;

import com.balazs.hajdu.domain.response.MotionRequest;
import com.balazs.hajdu.facade.MeasurementResultFacade;
import com.balazs.hajdu.service.RequestValidator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Optional;

/**
 * A controller to handle motion operation.
 *
 * @author Balazs Hajdu
 */
@RestController
public class MotionController {

    @Inject
    private MeasurementResultFacade measurementResultFacade;

    @Inject
    private RequestValidator requestValidator;

    @RequestMapping(value = "/{username}/sensors/{sensorName}/detected", method = RequestMethod.POST)
    public MotionRequest saveDetectedMotion(@PathVariable String username,
                                            @PathVariable String sensorName,
                                            @RequestBody MotionRequest motionRequest) {
        if (requestValidator.isValidUser(username, motionRequest.getKey())) {
            measurementResultFacade.saveMeasurementResult(username, sensorName, Optional.empty());
        }

        return motionRequest;
    }
}
