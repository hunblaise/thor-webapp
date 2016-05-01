package com.balazs.hajdu.client.service.impl;

import com.balazs.hajdu.client.domain.config.Bmp180Configuration;
import com.balazs.hajdu.client.domain.response.MeasurementResult;
import com.balazs.hajdu.client.domain.response.TemperatureSensorResponse;
import com.balazs.hajdu.client.repository.PressureRepository;
import com.balazs.hajdu.client.repository.TemperatureRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.testng.Assert.*;

/**
 * @author Balazs Hajdu
 */
public class Bmp180DataConversionServiceTest {

    private static final double TEST_TEMPERATURE_VALUE = 15.0;
    private static final double TEST_PRESSURE_VALUE = 7831.0;

    private static final short TEST_AC1 = 408;
    private static final short TEST_AC2 = -72;
    private static final short TEST_AC3 = -14383;
    private static final int TEST_AC4 = 32741;
    private static final int TEST_AC5 = 32757;
    private static final int TEST_AC6 = 23153;
    private static final short TEST_B1 = 6190;
    private static final short TEST_B2 = 4;
    private static final short TEST_MB = -32768;
    private static final short TEST_MC = -8711;
    private static final short TEST_MD = 2868;

    private static final int UT = 27898;
    private static final int UP = 23843;

    @Mock
    private TemperatureRepository temperatureRepository;

    @Mock
    private PressureRepository pressureRepository;

    @Mock
    private Bmp180Configuration configuration;

    @InjectMocks
    private Bmp180DataConversionService bmp180DataConversionService;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvertMeasurementsToNormalValue() {
        // given
        TemperatureSensorResponse temperatureSensorResponse = aTemperatureSensorResponse();
        given(configuration.getAc1()).willReturn(TEST_AC1);
        given(configuration.getAc2()).willReturn(TEST_AC2);
        given(configuration.getAc3()).willReturn(TEST_AC3);
        given(configuration.getAc4()).willReturn(TEST_AC4);
        given(configuration.getAc5()).willReturn(TEST_AC5);
        given(configuration.getAc6()).willReturn(TEST_AC6);
        given(configuration.getB1()).willReturn(TEST_B1);
        given(configuration.getB2()).willReturn(TEST_B2);
        given(configuration.getMb()).willReturn(TEST_MB);
        given(configuration.getMc()).willReturn(TEST_MC);
        given(configuration.getMd()).willReturn(TEST_MD);

        given(temperatureRepository.readTemperature()).willReturn(UT);
        given(pressureRepository.readPressure()).willReturn(UP);

        // when
        TemperatureSensorResponse actual = bmp180DataConversionService.convertData();

        // then
        assertThat(actual, is(temperatureSensorResponse));
    }

    private TemperatureSensorResponse aTemperatureSensorResponse() {
        return new TemperatureSensorResponse.Builder()
                .withTemperature(new MeasurementResult.Builder().withValue(TEST_TEMPERATURE_VALUE).build())
                .withPressure(new MeasurementResult.Builder().withValue(TEST_PRESSURE_VALUE).build())
                .build();
    }
}