package com.balazs.hajdu.client.repository;

import com.balazs.hajdu.client.domain.config.Bmp180Configuration;
import com.balazs.hajdu.client.error.TemperatureSensorException;
import com.balazs.hajdu.client.repository.impl.Bmp180TemperatureRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    private static final int READ_TIMEOUT = 50000;
    private static final int CONNECT_TIMEOUT = 1000;
    private static final String LOCAL_DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public I2CDevice bmp180() throws IOException {
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        LOGGER.debug(CONNECTED_TO_BUS);

        I2CDevice bmp180 = bus.getDevice(DEVICE_ADDRESS);
        LOGGER.debug(FOUND_SENSOR + bmp180);

        return bmp180;
    }

    @Bean
    public Bmp180Configuration configuration() {
        Bmp180Configuration configuration = null;

        try {
            byte[] bytes = new byte[TEMPERATURE_CONTROL_REGISTER_DATA_CALIBRATION_BYTES];
            int readTotal = bmp180().read(EEPROM_START, bytes, OFFSET, TEMPERATURE_CONTROL_REGISTER_DATA_CALIBRATION_BYTES);

            if (readTotal != TEMPERATURE_CONTROL_REGISTER_DATA_CALIBRATION_BYTES) {
                throw new TemperatureSensorException(CAN_NOT_CALIBRATE_SENSOR);
            }

            DataInputStream calibrationInputStream = new DataInputStream(new ByteArrayInputStream(bytes));

            configuration = new Bmp180Configuration.Builder().withDevice(bmp180())
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

            LOGGER.debug("Configuration data: " + configuration);

        } catch (IOException e) {
            LOGGER.error(CAN_NOT_READ_SENSOR, e);
        } catch (TemperatureSensorException e) {
            LOGGER.error(CAN_NOT_CALIBRATE_SENSOR, e);
        }

        return configuration;
    }

    @Bean
    public GpioPinDigitalInput motionSensor() {
        GpioController gpioController = GpioFactory.getInstance();
        return gpioController.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
    }

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMATTER)));

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        jsonMessageConverter.setObjectMapper(objectMapper);

        messageConverters.add(jsonMessageConverter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(READ_TIMEOUT);
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        return factory;
    }

}
