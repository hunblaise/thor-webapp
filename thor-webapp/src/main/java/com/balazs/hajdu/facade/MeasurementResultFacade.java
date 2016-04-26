package com.balazs.hajdu.facade;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.domain.view.DateIntervalRequestForm;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;

import java.util.List;

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
    MeasurementResult saveMeasurementResult(String userName, String sensorName, MeasurementResultRequestForm requestForm);

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

}
