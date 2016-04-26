package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.repository.MeasurementResultRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Default implementation for {@link com.balazs.hajdu.repository.MeasurementResultRepositoryCustom}.
 *
 * @author Balazs Hajdu
 */
public class MeasurementResultRepositoryImpl implements MeasurementResultRepositoryCustom {

    private static final String USERNAME_FIELD = "username";
    private static final String SENSORNAME_FIELD = "sensorName";
    private static final String DATE_FIELD = "date";

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementResultRepositoryImpl.class);

    @Inject
    private MongoTemplate mongoTemplate;

    @Inject
    private MeasurementResultTransformer transformer;

    @Override
    public List<MeasurementResult> getMeasurementResultsBetweenDateRange(MeasurementResultQueryContext queryContext) {
        List<MeasurementResultEntity> entities = mongoTemplate.find(new Query(where(USERNAME_FIELD).is(queryContext.getUsername())
                        .and(SENSORNAME_FIELD).is(queryContext.getSensorName())
                        .and(DATE_FIELD).gte(queryContext.getStartDate()).lt(queryContext.getEndDate())),
                MeasurementResultEntity.class);

        return transformer.transformToThor(entities);
    }

    @Override
    public void saveMeasurementResultToSensor(MeasurementResult measurementResult) {
        MeasurementResultEntity measurementResultEntity = transformer.transform(measurementResult);

        mongoTemplate.insert(measurementResultEntity);

        LOGGER.debug("Saved to the database a new measurement result: {}", measurementResultEntity);
    }

}
