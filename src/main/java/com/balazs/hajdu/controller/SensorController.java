package com.balazs.hajdu.controller;

import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * A controller to communicate with the sensor.
 *
 * @author Balazs Hajdu
 */
@RestController
@RequestMapping("/save/{sensorName}")
public class SensorController {

    @RequestMapping(method = RequestMethod.POST)
    public Sensor addMeasurementValue(@PathVariable String sensorName,
                                      @RequestBody MeasurementResultRequestForm measurementResult) {
        Sensor.Builder builder = new Sensor.Builder();

        builder.withSensorName(sensorName);
        builder.withValue(measurementResult.getValue());
        builder.withLocation(new GeoJsonPoint(measurementResult.getLat(), measurementResult.getLon()));

        return builder.build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getMeasurmentValue(@PathVariable String sensorName) {
        return sensorName;
    }
}
