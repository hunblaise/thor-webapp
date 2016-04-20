package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.StatisticsInterval;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Transforms Thor related domain objects to database related domain objects.
 *
 * @author Balazs Hajdu
 */
@Component
public class SensorFactory {

    /**
     * Transforms database related database domain object to Thor related domain object.
     *
     * @param sensorEntities database related domain object
     * @return Thor related domain object
     */
    public List<Sensor> transform(List<SensorEntity> sensorEntities, Map<String, List<MeasurementResult>> measurementResults,
                                  Map<String, Map<StatisticsInterval, MeasurementResultStatistics>> statistics) {
        return sensorEntities.stream()
                .map(sensorEntity -> new Sensor.Builder().withId(sensorEntity.getId())
                        .withSensorName(sensorEntity.getName())
                        .withLocation(sensorEntity.getLocation().getX(), sensorEntity.getLocation().getY())
                        .withMaxAlert(sensorEntity.getMaxAlert())
                        .withMinAlert(sensorEntity.getMinAlert())
                        .withMeasurementResults(measurementResults.get(sensorEntity.getName()))
                        .withMeasurementResultStatistics(statistics.get(sensorEntity.getName()))
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

}
