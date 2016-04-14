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
     * Tranforms Thor related domain objects to database related domain objects.
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

    public List<MeasurementResponse> transform(List<MeasurementResultEntity> entities) {
        return entities.stream()
                .map(this::transform)
                .collect(Collectors.toList());
    }

    private MeasurementResponse transform(MeasurementResultEntity entity) {
        return new MeasurementResponse.Builder().withValue(entity.getValue())
                .withDate(entity.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

}
