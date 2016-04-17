package com.balazs.hajdu.facade.impl;

import com.balazs.hajdu.adapter.GeoAdapter;
import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.components.transformers.SensorFactory;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.StatisticsInterval;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.balazs.hajdu.domain.view.SensorRequestForm;
import com.balazs.hajdu.error.exceptions.InvalidDatabaseOperationException;
import com.balazs.hajdu.facade.SensorFacade;
import com.balazs.hajdu.service.MeasurementResultService;
import com.balazs.hajdu.service.SensorService;
import com.balazs.hajdu.service.StatisticsService;
import com.balazs.hajdu.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link com.balazs.hajdu.facade.SensorFacade}.
 *
 * @author Balazs Hajdu
 */
@Component
public class SensorFacadeImpl implements SensorFacade {

    private static final String CAN_NOT_FIND_LOCATION = "Can not find location for address: ";

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorFacadeImpl.class);

    @Inject
    private SensorFactory sensorFactory;

    @Inject
    private MeasurementResultTransformer measurementResultTransformer;

    @Inject
    private UserService userService;

    @Inject
    private SensorService sensorService;

    @Inject
    private StatisticsService statisticsService;

    @Inject
    private MeasurementResultService measurementResultService;

    @Inject
    private GeoAdapter geoAdapter;

    @Override
    public List<Sensor> getAllSensorByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        Map<String, List<MeasurementResult>> measurementResults = mapMeasurementResults(
                measurementResultService.getMeasurementResultsByUsername(username));
        Map<String, Map<StatisticsInterval, MeasurementResultStatistics>> statisticsMap = buildStatisticsMap(username, userEntity.getSensors());

        return sensorFactory.transform(userEntity.getSensors(), measurementResults, statisticsMap);
    }

    @Override
    public Sensor saveSensor(String username, SensorRequestForm sensorRequestForm) throws InvalidDatabaseOperationException {
        List<GeocodedLocation> locations = geoAdapter.geocodeAddress(sensorRequestForm.getAddress());

        Sensor sensor;
        if (!locations.isEmpty()) {
            sensor = sensorService.saveSensor(username, buildSensor(locations, sensorRequestForm));
        } else {
            LOGGER.error(CAN_NOT_FIND_LOCATION, sensorRequestForm.getAddress());
            throw new InvalidDatabaseOperationException(CAN_NOT_FIND_LOCATION);
        }

        return sensor;
    }

    private Map<String, List<MeasurementResult>> mapMeasurementResults(List<MeasurementResultEntity> measurementResults) {
        return measurementResults.stream()
                .map(entity -> measurementResultTransformer.transformToThor(entity))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(MeasurementResult::getSensorName));
    }

    private Sensor buildSensor(List<GeocodedLocation> locations, SensorRequestForm sensorRequestForm) {
        return new Sensor.Builder().withSensorName(sensorRequestForm.getSensorName())
                .withId(new ObjectId())
                .withLocation(locations.get(0).getLocation().getLat(), locations.get(0).getLocation().getLon())
                .withMeasurementResults(Collections.emptyList())
                .build();
    }

    private Map<String, Map<StatisticsInterval, MeasurementResultStatistics>> buildStatisticsMap(String username, List<SensorEntity> sensorEntities) {
        Map<String, Map<StatisticsInterval, MeasurementResultStatistics>> statistics = new HashMap<>();

        for (SensorEntity sensorEntity : sensorEntities) {
            statistics.put(sensorEntity.getName(), calculateStatistics(username, sensorEntity.getName()));
        }

        return statistics;
    }

    private Map<StatisticsInterval, MeasurementResultStatistics> calculateStatistics(String username, String sensorName) {
        Map<StatisticsInterval, MeasurementResultStatistics> statisticsMap = new EnumMap<>(StatisticsInterval.class);

        for (StatisticsInterval interval : StatisticsInterval.values()) {
            statisticsMap.put(interval, statisticsService.getStatistics(username, sensorName, interval));
        }

        return statisticsMap;
    }

}