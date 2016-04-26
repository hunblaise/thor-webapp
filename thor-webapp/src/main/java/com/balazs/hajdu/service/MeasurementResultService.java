package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.repository.MeasurementResultRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * A service to handle measurement related business logic and communicate with the repository layer.
 *
 * @author Balazs Hajdu
 */
@Service
public class MeasurementResultService {

    @Inject
    private MeasurementResultRepository measurementResultRepository;

    /**
     * Retrieves all of the measurement results for the given sensor.
     *
     * @param username username
     * @param sensorName sensor name
     * @return measurement results
     */
    public List<MeasurementResultEntity> getAllMeasurementResultBySensorName(String username, String sensorName) {
        return measurementResultRepository.findByUsernameAndSensorNameAllIgnoreCase(username, sensorName);
    }

    /**
     * Retrieves measurement results by username and sensor's name.
     *
     * @param username username
     * @param sensorName sensor's name
     * @return measurement results
     */
    public List<MeasurementResultEntity> getMeasurementResultsByUsernameAndSensorName(String username, String sensorName) {
        return measurementResultRepository.findByUsernameAndSensorNameAllIgnoreCase(username, sensorName);
    }

    /**
     * Retrieves measurement results by username.
     *
     * @param username username
     * @return measurement results
     */
    public List<MeasurementResultEntity> getMeasurementResultsByUsername(String username) {
        return measurementResultRepository.findByUsernameIgnoreCase(username);
    }

    /**
     * Save a new measurement result to the given user.
     *
     * @param measurementResult measurement result
     * @return saved measurement result
     */
    public MeasurementResult saveMeasurementResultToSensor(MeasurementResult measurementResult) {
        measurementResultRepository.saveMeasurementResultToSensor(measurementResult);

        return measurementResult;
    }

    /**
     * A method to retrieve measurement results from a given time interval.
     *
     * @param context query context
     * @return measurement results from the given interval
     */
    public List<MeasurementResult> getLastsMeasurementResultsFromDateInterval(MeasurementResultQueryContext context) {
        return measurementResultRepository.getMeasurementResultsBetweenDateRange(context);
    }

}
