package com.balazs.hajdu.client.repository.impl;

import com.balazs.hajdu.client.domain.config.Bmp180Mode;
import com.balazs.hajdu.client.repository.PressureRepository;
import com.balazs.hajdu.client.util.ThreadUtilities;
import com.pi4j.io.i2c.I2CDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Default implementation of {@link com.balazs.hajdu.client.repository.PressureRepository}.
 *
 * @author Balazs Hajdu
 */
@Repository
public class Bmp180PressureRepository implements PressureRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bmp180PressureRepository.class);

    private static final String PRESSURE_ERROR_MESSAGE = "Error occurred during pressure reading";

    private static final int PRESSURE_CONTROL_REGISTER = 0xF4;
    private static final byte PRESSURE_READ_COMMAND = (byte) 0x34;
    private static final int PRESSURE_READ_ADDRESS = 0xF6;

    @Inject
    private I2CDevice bmp180;

    @Override
    public int readPressure() {

        int uncompensatedPressure = -1;
        try {
            bmp180.write(PRESSURE_CONTROL_REGISTER, (byte)(PRESSURE_READ_COMMAND + (Bmp180Mode.ULTRA_HIGH_RESOLUTION.getOverSampleSettingValue() << 6)));
            ThreadUtilities.waitFor(Bmp180Mode.ULTRA_HIGH_RESOLUTION.getMinimumConversionTime());

            int msb = bmp180.read(PRESSURE_READ_ADDRESS);
            int lsb = bmp180.read(PRESSURE_READ_ADDRESS + 1);
            int xlsb = bmp180.read(PRESSURE_READ_ADDRESS + 2);

            uncompensatedPressure = ((msb << 16) + (lsb << 8) + xlsb) >> (8 - Bmp180Mode.ULTRA_HIGH_RESOLUTION.getOverSampleSettingValue());
        } catch (IOException e) {
            LOGGER.error(PRESSURE_ERROR_MESSAGE, e);
        }

        return uncompensatedPressure;
    }
}
