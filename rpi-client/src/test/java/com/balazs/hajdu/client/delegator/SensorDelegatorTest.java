package com.balazs.hajdu.client.delegator;

import com.balazs.hajdu.client.service.TemperatureService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author Balazs Hajdu
 */
public class SensorDelegatorTest {

    private static final String TEMPERATURE_SENSOR_NAME = "temperature";

    @Mock
    private TemperatureService temperatureService;

    @InjectMocks
    private SensorDelegator sensorDelegator;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTemperatureValue() {
        // given
        given(temperatureService.calculateTemperature()).willReturn(21.0);

        // when
        double actual = sensorDelegator.delegate(TEMPERATURE_SENSOR_NAME);

        // then
        assertThat(actual, is(21.0));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionOnInvalidSensorName() {
        // given
        given(temperatureService.calculateTemperature()).willReturn(21.0);

        // when
        double actual = sensorDelegator.delegate("invalid-sensor-name");

        // then
    }

}