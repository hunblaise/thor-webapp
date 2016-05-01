package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.maps.Coordinates;

import java.util.List;

/**
 * An interface to implement more advanced measurement result related queries.
 *
 * @author Balazs Hajdu
 */
public interface MeasurementResultRepositoryCustom {

    /**
     * Get measurement results from a date range.
     * @param queryContext query context
     * @return a list of measurement results based on the query
     */
    List<MeasurementResult> getMeasurementResultsBetweenDateRange(MeasurementResultQueryContext queryContext);

    /**
     * Save a new measurement result to the given sensor.
     *
     * @param measurementResult measurement result
     */
    void saveMeasurementResultToSensor(MeasurementResult measurementResult);


    /**
     * Retrieves measurement results from a given area.
     *
     * @param coordinates area
     * @return measurement results
     */
    List<MeasurementResult> getMeasurementResultsFromLocation(Coordinates coordinates);

}
