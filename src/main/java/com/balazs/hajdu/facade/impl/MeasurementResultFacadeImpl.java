package com.balazs.hajdu.facade.impl;

import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.facade.MeasurementResultFacade;
import com.balazs.hajdu.service.MeasurementResultService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Balazs Hajdu
 */
@Component
public class MeasurementResultFacadeImpl implements MeasurementResultFacade {

    private static final int LAST_TWO_WEEKS_MEASUREMENT_NUMBER = 336;

    @Inject
    private MeasurementResultService measurementResultService;

    @Inject
    private MeasurementResultTransformer measurementResultTransformer;

    @Override
    public MeasurementResult saveMeasurementResult(String userName, String sensorName, MeasurementResultRequestForm requestForm) {

        MeasurementResult result = new MeasurementResult.Builder().withValue(requestForm.getValue())
                .withLocation(requestForm.getLat(), requestForm.getLon())
                .withDate(LocalDateTime.now())
                .withUsername(userName)
                .withSensorName(sensorName)
                .build();

        return measurementResultService.saveMeasurementResultToSensor(result);
    }

    @Override
    public List<MeasurementResponse> getAllMeasurementResultsForSensor(String username, String sensorName) {
        List<MeasurementResultEntity> measurementResults = measurementResultService.getMeasurementResultsByUsernameAndSensorName(username, sensorName);

        return measurementResultTransformer.transformToResponse(measurementResults);
    }

    @Override
    public List<MeasurementResponse> getLastTwoWeeksMeasurementResulstForSensor(String username, String sensorName) {
        List<MeasurementResultEntity> measurementResults = measurementResultService.getMeasurementResultsByUsernameAndSensorName(username, sensorName);

        return measurementResultTransformer.transformToResponse(measurementResults.stream()
                .limit(LAST_TWO_WEEKS_MEASUREMENT_NUMBER)
                .collect(Collectors.toList()));
    }
}
