package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.repository.MeasurementResultRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * A service to handle motion related operations.
 *
 * @author Balazs Hajdu
 */
@Service
public class MotionService {

    private static final String MOTION_SENSOR = "motion";

    @Inject
    private MeasurementResultRepository measurementResultRepository;

    public boolean isMotionDetected(String username, LocalDateTime date) {
        MeasurementResultQueryContext queryContext = new MeasurementResultQueryContext.Builder()
                .withUsername(username)
                .withSensorName(MOTION_SENSOR)
                .withStartDate(date)
                .withEndDate(LocalDateTime.now())
                .build();

        return !measurementResultRepository.getMeasurementResultsBetweenDateRange(queryContext).isEmpty();
    }

}
