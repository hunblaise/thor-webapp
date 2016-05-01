package com.balazs.hajdu.client.repository.impl;

import com.pi4j.io.i2c.I2CDevice;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Balazs Hajdu
 */
public class Bmp180PressureRepositoryTest {

    @Mock
    private I2CDevice bmp180;

    @InjectMocks
    private Bmp180PressureRepository pressureRepository;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReadPressure() throws IOException {
        // given

        // when
        int actual = pressureRepository.readPressure();

        // then
        verify(bmp180, times(1)).write(anyInt(), anyByte());
        verify(bmp180, times(3)).read(anyInt());
    }
}