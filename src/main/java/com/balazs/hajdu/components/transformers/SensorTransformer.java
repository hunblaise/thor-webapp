package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.SensorEntity;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Transforms Thor related domain objects to database related domain objects.
 *
 * @author Balazs Hajdu
 */
@Component
public class SensorTransformer {

    @Inject
    private MeasurementResultTransformer measurementResultTransformer;

    /**
     * Transforms database related database domain object to Thor related domain object.
     *
     * @param sensorEntities database related domain object
     * @return Thor related domain object
     */
    public List<Sensor> transform(List<SensorEntity> sensorEntities) {
        return sensorEntities.stream()
                .map(sensorEntity -> new Sensor.Builder().withId(sensorEntity.getId())
                        .withSensorName(sensorEntity.getName())
                        .withLocation(sensorEntity.getLocation().getX(), sensorEntity.getLocation().getY())
                        .withMeasurementResults(transMeasurementResults(sensorEntity.getMeasurementResults()))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Transforms Thor related domain object to database realted domain object.
     *
     * @param sensor Thor related domain object
     * @return database related domain object
     */
    public SensorEntity transform(Sensor sensor) {
        SensorEntity sensorEntity = new SensorEntity();

        sensorEntity.setName(sensor.getName());
        sensorEntity.setLocation(sensor.getLocation());
        sensorEntity.setMeasurementResults(transformMeasurementResults(sensor.getMeasurementResults()));
        sensorEntity.setId(sensor.getId());

        return sensorEntity;
    }

    private List<MeasurementResultEntity> transformMeasurementResults(List<MeasurementResult> measurementResults) {
        return measurementResults.stream()
                .map(measurementResult -> measurementResultTransformer.transform(measurementResult))
                .collect(Collectors.toList());
    }

    private List<MeasurementResult> transMeasurementResults(List<MeasurementResultEntity> measurementResultEntities) {
        return measurementResultEntities.stream()
                .map(measurementResultEntity -> new MeasurementResult.Builder()
                        .withValue(measurementResultEntity.getValue())
                        .withDate(measurementResultEntity.getDate())
                        .build())
                .collect(Collectors.toList());
    }

}
