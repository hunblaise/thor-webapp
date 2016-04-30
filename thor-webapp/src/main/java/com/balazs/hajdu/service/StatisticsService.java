package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.StatisticsInterval;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.balazs.hajdu.repository.MeasurementResultRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * A service to handle statistics related business logic.
 *
 * @author Balazs Hajdu
 */
@Service
public class StatisticsService {

    private static final int DECIMAL_PLACES = 2;

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
    public Optional<MeasurementResultStatistics> getStatistics(String username, String sensorName, StatisticsInterval interval) {

        List<MeasurementResult> results = measurementResultRepository.getMeasurementResultsBetweenDateRange(new MeasurementResultQueryContext.Builder()
                .withUsername(username)
                .withSensorName(sensorName)
                .withStartDate(LocalDateTime.now().minusDays(interval.getInterval()))
                .withEndDate(LocalDateTime.now())
                .build());

        return !results.isEmpty() ? Optional.of(buildStatistics(results)) : Optional.empty();
    }

    private MeasurementResultStatistics buildStatistics(List<MeasurementResult> results) {
        return new MeasurementResultStatistics.Builder()
                .withAverage(new BigDecimal(results.stream()
                        .mapToDouble(MeasurementResult::getValue)
                        .average()
                        .getAsDouble())
                        .setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue())
                .withMax(new BigDecimal(results.stream()
                        .mapToDouble(MeasurementResult::getValue)
                        .max()
                        .getAsDouble()).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP).doubleValue())
                .withMin(new BigDecimal(results.stream()
                        .mapToDouble(MeasurementResult::getValue)
                        .min()
                        .getAsDouble()).setScale(DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).doubleValue())
                .build();
    }

}
