package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.repository.MeasurementResultRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Default implementation for {@link com.balazs.hajdu.repository.MeasurementResultRepositoryCustom}.
 *
 * @author Balazs Hajdu
 */
public class MeasurementResultRepositoryImpl implements MeasurementResultRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementResultRepositoryImpl.class);

    private static final String USERNAME_FIELD = "username";
    private static final String SENSORNAME_FIELD = "sensorName";
    private static final String DATE_FIELD = "date";
    private static final String LOCATION_FIELD = "location";
    private static final String TEMPERATURE_SENSOR = "temperature";
    private static final LocalDateTime PAST_FIVE_DAY = LocalDateTime.now().minusDays(4);
    private static final double DEFAULT_DISTANCE = 15;

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private MeasurementResultTransformer transformer;

    @Override
    public List<MeasurementResult> getMeasurementResultsBetweenDateRange(MeasurementResultQueryContext queryContext) {
        List<MeasurementResultEntity> entities = mongoTemplate.find(
                new Query(where(USERNAME_FIELD).is(queryContext.getUsername())
                        .and(SENSORNAME_FIELD).is(queryContext.getSensorName())
                        .and(DATE_FIELD).gte(queryContext.getStartDate()).lt(queryContext.getEndDate())),
                MeasurementResultEntity.class);

        LOGGER.debug("Measurement results between {} and {}: {}", queryContext.getStartDate(), queryContext.getEndDate(), entities);

        return transformer.transformToThor(entities);
    }

    @Override
    public void saveMeasurementResultToSensor(MeasurementResult measurementResult) {
        MeasurementResultEntity measurementResultEntity = transformer.transform(measurementResult);

        mongoTemplate.insert(measurementResultEntity);

        LOGGER.debug("Saved to the database a new measurement result: {}", measurementResultEntity);
    }

    @Override
    public List<MeasurementResult> getMeasurementResultsFromLocation(Coordinates coordinates) {
        Point point = new Point(coordinates.getLat(), coordinates.getLon());
        Distance distance = new Distance(DEFAULT_DISTANCE, Metrics.KILOMETERS);

        List<MeasurementResultEntity> measurementResults = mongoTemplate.find(
                new Query(Criteria.where(SENSORNAME_FIELD).is(TEMPERATURE_SENSOR)
                        .and(DATE_FIELD).gte(PAST_FIVE_DAY)
                        .and(LOCATION_FIELD)
                        .nearSphere(point)
                        .maxDistance(distance.getNormalizedValue())), MeasurementResultEntity.class);

        return transformer.transformToThor(measurementResults);
    }

}
