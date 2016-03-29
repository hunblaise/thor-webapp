package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.repository.UserRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation for {@link com.balazs.hajdu.repository.UserRepositoryCustom}.
 *
 * @author Balazs Hajdu
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public void addNewMeasurement(String userName, String sensorName, MeasurementResult measurementResult) {
        LOGGER.error("update: {}", measurementResult);
    }

}
