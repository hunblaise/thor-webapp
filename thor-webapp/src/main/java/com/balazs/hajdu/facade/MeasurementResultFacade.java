package com.balazs.hajdu.facade;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.domain.response.WeatherSearchResponse;
import com.balazs.hajdu.domain.view.DateIntervalRequestForm;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.WeatherSearchQueryForm;

import java.util.List;
import java.util.Optional;

/**
 * Measurement result facade.
 *
 * @author Balazs Hajdu
 */
public interface MeasurementResultFacade {

    /**
     * A method to save new measurement result.
     *
     * @param userName username
     * @param sensorName sensor's name
     * @param requestForm request form
     * @return saved measurement result
     */
    MeasurementResult saveMeasurementResult(String userName, String sensorName, Optional<MeasurementResultRequestForm> requestForm);

    /**
     * A method to retrieve all measurement results from a sensor.
     *
     * @param username username
     * @param sensorName sensor's name
     * @return measurement results
     */
    List<MeasurementResponse> getAllMeasurementResultsForSensor(String username, String sensorName);

    /**
     * A method to retrieve measurement results from a given time interval.
     *
     * @param username username
     * @param sensorName sensor's name
     * @param requestForm request form
     * @return measurement results
     */
    List<MeasurementResponse> getMeasurementResultsFromDateInterval(String username, String sensorName, DateIntervalRequestForm requestForm);

    /**
     * A method to retrieve weather in a given location.
     *
     * @param weatherSearchQueryForm query form
     * @return weather search response
     */
    Optional<WeatherSearchResponse> searchWeatherInLocation(WeatherSearchQueryForm weatherSearchQueryForm);

    /**
     * Retrieve a new measurement result and save it.
     *
     * @param username username
     * @param sensorName sensor name
     * @return saved measurement result
     */
    MeasurementResponse updateSensorWithNewMeasurement(String username, String sensorName);

}
