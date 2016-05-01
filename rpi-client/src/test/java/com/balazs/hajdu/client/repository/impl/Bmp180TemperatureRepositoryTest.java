package com.balazs.hajdu.client.repository.impl;

import com.pi4j.io.i2c.I2CDevice;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Balazs Hajdu
 */
public class Bmp180TemperatureRepositoryTest {

    private static final int TEMPERATURE_DATA_LENGTH = 2;
    private static final int READ_OFFSET = 0;
    private static final int READ_SIZE = 2;

    private static final int TEMPERATURE_CONTROL_REGISTER_DATA_CONTROL_REGISTER = 0xF4;
    private static final byte TEMPERATURE_READ_COMMAND = (byte) 0x2E;
    private static final byte TEMPERATURE_READ_ADDRESS = (byte) 0xF6;

    @Mock
    private I2CDevice bmp180;

    @InjectMocks
    private Bmp180TemperatureRepository temperatureRepository;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReadTemperature() throws IOException {
        // given
        byte[] bytes = new byte[TEMPERATURE_DATA_LENGTH];
        given(bmp180.read(TEMPERATURE_READ_ADDRESS, bytes, READ_OFFSET, READ_SIZE)).willReturn(2);

        // when
        int actual = temperatureRepository.readTemperature();

        // then
        verify(bmp180, times(1)).write(TEMPERATURE_CONTROL_REGISTER_DATA_CONTROL_REGISTER, TEMPERATURE_READ_COMMAND);
        verify(bmp180, times(1)).read(anyByte(), any(byte[].class), anyInt(), anyInt());
    }

}