package com.balazs.hajdu.client.repository.impl;

import com.balazs.hajdu.client.domain.config.Bmp180Configuration;
import com.balazs.hajdu.client.error.TemperatureSensorException;
import com.balazs.hajdu.client.repository.TemperatureRepository;
import com.balazs.hajdu.client.util.ThreadUtilities;
import com.pi4j.io.i2c.I2CDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Default implementation of {@link com.balazs.hajdu.client.repository.TemperatureRepository}.
 * @author Balazs Hajdu
 */
public class Bmp180TemperatureRepository implements TemperatureRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bmp180TemperatureRepository.class);

    private static final String TEMPERATURE_READING_ERROR = "Error occurred during temperature reading";
    private static final int TEMPERATURE_DATA_LENGTH = 2;
    private static final int READ_OFFSET = 0;
    private static final int READ_SIZE = 2;

    private static final int TEMPERATURE_CONTROL_REGISTER_DATA_CONTROL_REGISTER = 0xF4;
    private static final byte TEMPERATURE_READ_COMMAND = (byte) 0x2E;
    private static final byte TEMPERATURE_READ_ADDRESS = (byte) 0xF6;

    private final I2CDevice bmp180;
    private final Bmp180Configuration configuration;

    public Bmp180TemperatureRepository(Bmp180Configuration configuration) {
        this.bmp180 = configuration.getDevice();
        LOGGER.debug("bmp180: " + bmp180);
        this.configuration = configuration;
    }

    @Override
    public int readTemperature() {
        byte[] bytes = new byte[TEMPERATURE_DATA_LENGTH];

        int uncompensatedTemperatureData = -1;
        try {
            bmp180.write(TEMPERATURE_CONTROL_REGISTER_DATA_CONTROL_REGISTER, TEMPERATURE_READ_COMMAND);
            ThreadUtilities.waitFor(500);

            int rawData = bmp180.read(TEMPERATURE_READ_ADDRESS, bytes, READ_OFFSET, READ_SIZE);
            LOGGER.debug("Raw read data: " + rawData);

            if (rawData < READ_SIZE) {
                throw new TemperatureSensorException(TEMPERATURE_READING_ERROR);
            }

            DataInputStream bmp180InputStream = new DataInputStream(new ByteArrayInputStream(bytes));

            uncompensatedTemperatureData = bmp180InputStream.readUnsignedShort();
        } catch (IOException e) {
            LOGGER.error(TEMPERATURE_READING_ERROR);
        } catch (TemperatureSensorException e) {
            LOGGER.error(TEMPERATURE_READING_ERROR, e);
        }

        return uncompensatedTemperatureData;
    }

    @Override
    public Bmp180Configuration getConfiguration() {
        return configuration;
    }

}
