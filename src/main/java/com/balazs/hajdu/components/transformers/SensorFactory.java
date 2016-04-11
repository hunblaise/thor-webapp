package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.repository.MeasurementResultRepository;
import com.balazs.hajdu.repository.UserRepository;
import org.bson.types.ObjectId;
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
public class SensorFactory {

    @Inject
    private MeasurementResultTransformer measurementResultTransformer;

    @Inject
    private MeasurementResultRepository measurementResultRepository;

    /**
     * Transforms database related database domain object to Thor related domain object.
     *
     * @param sensorEntities database related domain object
     * @return Thor related domain object
     */
    public List<Sensor> transform(String username, List<SensorEntity> sensorEntities) {
        return sensorEntities.stream()
                .map(sensorEntity -> new Sensor.Builder().withId(sensorEntity.getId())
                        .withSensorName(sensorEntity.getName())
                        .withLocation(sensorEntity.getLocation().getX(), sensorEntity.getLocation().getY())
                        .withMeasurementResults(collectMeasurementResults(username, sensorEntity.getName()))
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

        sensorEntity.setId(new ObjectId());
        sensorEntity.setName(sensor.getName());
        sensorEntity.setLocation(sensor.getLocation());
        sensorEntity.setId(sensor.getId());

        return sensorEntity;
    }

    private List<MeasurementResult> transformMeasurementResults(List<MeasurementResultEntity> measurementResultEntities) {
        return measurementResultEntities.stream()
                .map(measurementResultEntity -> new MeasurementResult.Builder()
                        .withId(new ObjectId())
                        .withValue(measurementResultEntity.getValue())
                        .withDate(measurementResultEntity.getDate())
                        .withUsername(measurementResultEntity.getUsername())
                        .withSensorName(measurementResultEntity.getSensorName())
                        .withLocation(measurementResultEntity.getLocation().getX(), measurementResultEntity.getLocation().getY())
                        .build())
                .collect(Collectors.toList());
    }

    private List<MeasurementResult> collectMeasurementResults(String username, String sensorName) {
        return transformMeasurementResults(measurementResultRepository.findByUsernameAndSensorNameAllIgnoreCase(username,
                sensorName));
    }

}
