package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.StatisticsInterval;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.balazs.hajdu.repository.MeasurementResultRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A service to handle statistics related business logic.
 *
 * @author Balazs Hajdu
 */
@Service
public class StatisticsService {

    @Inject
    private MeasurementResultRepository measurementResultRepository;

    /**
     * A method to get measurement statistics.
     *
     * @param username username
     * @param sensorName sensorname
     * @param interval day interval from today
     * @return statistics
     */
    public MeasurementResultStatistics getStatistics(String username, String sensorName, StatisticsInterval interval) {

        List<MeasurementResult> results = measurementResultRepository.getMeasurementResultsBetweenDateRange(new MeasurementResultQueryContext.Builder()
                .withUsername(username)
                .withSensorName(sensorName)
                .withStartDate(LocalDateTime.now().minusDays(interval.getInterval()))
                .withEndDate(LocalDateTime.now())
                .build());

        return buildStatistics(results);
    }

    private MeasurementResultStatistics buildStatistics(List<MeasurementResult> results) {
        return new MeasurementResultStatistics.Builder()
                .withAverage(results.stream().mapToDouble(MeasurementResult::getValue).average().getAsDouble())
                .withMax(results.stream().mapToDouble(MeasurementResult::getValue).max().getAsDouble())
                .withMin(results.stream().mapToDouble(MeasurementResult::getValue).min().getAsDouble())
                .build();
    }

}
