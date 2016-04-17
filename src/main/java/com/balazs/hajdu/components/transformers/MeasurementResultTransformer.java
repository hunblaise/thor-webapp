package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Transforms database related domain objects to Thor related domain objects.
 *
 * @author Balazs Hajdu
 */
@Component
public class MeasurementResultTransformer {

    /**
     * Transforms Thor related domain objects to database related domain objects.
     *
     * @param measurementResult Thor related domain object
     * @return database related domain object
     */
    public MeasurementResultEntity transform(MeasurementResult measurementResult) {
        MeasurementResultEntity measurementResultEntity = new MeasurementResultEntity();

        measurementResultEntity.setId(measurementResult.getId());
        measurementResultEntity.setDate(measurementResult.getDate());
        measurementResultEntity.setValue(measurementResult.getValue());
        measurementResultEntity.setSensorName(measurementResult.getSensorName());
        measurementResultEntity.setUsername(measurementResult.getUsername());
        measurementResultEntity.setLocation(measurementResult.getLocation());

        return measurementResultEntity;
    }

    /**
     * Transforms database related domain object into REST response related domain object.
     *
     * @param entities database entities
     * @return REST related domain objects
     */
    public List<MeasurementResponse> transformToResponse(List<MeasurementResultEntity> entities) {
        return entities.stream()
                .map(this::transformToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Transforms Thor related domain object into REST response related domain object.
     *
     * @param results Thor related domain object
     * @return REST related domain objects
     */
    public List<MeasurementResponse> transformThorToResponse(List<MeasurementResult> results) {
        return results.stream()
                .map(this::transformThorToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Transforms database related domain objects to Thor related domain objects.
     *
     * @param entities database related domain objects.
     * @return Thor related domain objects.
     */
    public List<MeasurementResult> transformToThor(List<MeasurementResultEntity> entities) {
        return entities.stream()
                .map(this::transformToThor)
                .collect(Collectors.toList());
    }

    private MeasurementResponse transformToResponse(MeasurementResultEntity entity) {
        return new MeasurementResponse.Builder().withValue(entity.getValue())
                .withDate(entity.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    private MeasurementResponse transformThorToResponse(MeasurementResult result) {
        return new MeasurementResponse.Builder().withValue(result.getValue())
                .withDate(result.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public MeasurementResult transformToThor(MeasurementResultEntity entity) {
        return new MeasurementResult.Builder().withUsername(entity.getUsername())
                .withSensorName(entity.getSensorName())
                .withValue(entity.getValue())
                .withDate(entity.getDate())
                .withLocation(entity.getLocation().getX(), entity.getLocation().getY())
                .withId(entity.getId())
                .build();
    }

}
