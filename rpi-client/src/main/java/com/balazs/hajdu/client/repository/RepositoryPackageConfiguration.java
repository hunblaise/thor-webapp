package com.balazs.hajdu.client.repository;

import com.balazs.hajdu.client.domain.config.Bmp180Configuration;
import com.balazs.hajdu.client.error.TemperatureSensorException;
import com.balazs.hajdu.client.repository.impl.Bmp180TemperatureRepository;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Balazs Hajdu
 */
@Configuration
public class RepositoryPackageConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bmp180TemperatureRepository.class);

    private static final String CAN_NOT_READ_SENSOR = "Error happened during the sensor reading: ";
    private static final String CAN_NOT_CALIBRATE_SENSOR = "Could not read calibration bytes.";
    private static final String CONNECTED_TO_BUS = "Connected to the i2c bus";
    private static final String FOUND_SENSOR = "Found BMP180 sensor on the i2c bus: ";

    private static final int OFFSET = 0;
    private static final int TEMPERATURE_CONTROL_REGISTER_DATA_CALIBRATION_BYTES = 22;
    private static final int EEPROM_START = 0xAA;
    private static final int DEVICE_ADDRESS = 0x77;

    @Bean
    public Bmp180TemperatureRepository bmp180TemperatureRepository() throws IOException {
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        LOGGER.debug(CONNECTED_TO_BUS);

        I2CDevice bmp180 = bus.getDevice(DEVICE_ADDRESS);
        LOGGER.debug(FOUND_SENSOR + bmp180);

        Bmp180Configuration configuration = calibration(bmp180);

        return new Bmp180TemperatureRepository(configuration);
    }

    private Bmp180Configuration calibration(I2CDevice device) {
        Bmp180Configuration configuration = null;

        try {
            byte[] bytes = new byte[TEMPERATURE_CONTROL_REGISTER_DATA_CALIBRATION_BYTES];
            int readTotal = device.read(EEPROM_START, bytes, OFFSET, TEMPERATURE_CONTROL_REGISTER_DATA_CALIBRATION_BYTES);

            if (readTotal != TEMPERATURE_CONTROL_REGISTER_DATA_CALIBRATION_BYTES) {
                throw new TemperatureSensorException(CAN_NOT_CALIBRATE_SENSOR);
            }

            DataInputStream calibrationInputStream = new DataInputStream(new ByteArrayInputStream(bytes));

            configuration = new Bmp180Configuration.Builder().withDevice(device)
                    .withAc1(calibrationInputStream.readShort())
                    .withAc2(calibrationInputStream.readShort())
                    .withAc3(calibrationInputStream.readShort())
                    .withAc4(calibrationInputStream.readUnsignedShort())
                    .withAc5(calibrationInputStream.readUnsignedShort())
                    .withAc6(calibrationInputStream.readUnsignedShort())
                    .withB1(calibrationInputStream.readShort())
                    .withB2(calibrationInputStream.readShort())
                    .withMb(calibrationInputStream.readShort())
                    .withMc(calibrationInputStream.readShort())
                    .build();

        } catch (IOException e) {
            LOGGER.error(CAN_NOT_READ_SENSOR, e);
        } catch (TemperatureSensorException e) {
            LOGGER.error(CAN_NOT_CALIBRATE_SENSOR, e);
        }

        return configuration;
    }

}
