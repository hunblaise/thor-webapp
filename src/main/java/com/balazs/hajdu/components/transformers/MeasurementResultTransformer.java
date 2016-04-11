package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import org.springframework.stereotype.Component;

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

}
