package com.balazs.hajdu.facade;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;

import java.util.List;

/**
 * Measurement result facade.
 *
 * @author Balazs Hajdu
 */
public interface MeasurementResultFacade {

    MeasurementResult saveMeasurementResult(String userName, String sensorName, MeasurementResultRequestForm requestForm);

    List<MeasurementResponse> getAllMeasurementResultsForSensor(String username, String sensorName);

    List<MeasurementResponse> getLastTwoWeeksMeasurementResulstForSensor(String username, String sensorName);

}
