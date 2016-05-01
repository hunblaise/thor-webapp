package com.balazs.hajdu.client.delegator;

import com.balazs.hajdu.client.domain.response.MeasurementResult;
import com.balazs.hajdu.client.domain.response.TemperatureSensorResponse;
import com.balazs.hajdu.client.service.DataConversionService;
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
    private static final String PRESSURE_SENSOR_NAME = "pressure";

    @Mock
    private DataConversionService dataConversionService;

    @InjectMocks
    private SensorDelegator sensorDelegator;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTemperatureValue() {
        // given
        given(dataConversionService.convertData()).willReturn(aTemperatureResponse());

        // when
        double actual = sensorDelegator.delegate(TEMPERATURE_SENSOR_NAME);

        // then
        assertThat(actual, is(21.0));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionOnInvalidSensorName() {
        // given
        given(dataConversionService.convertData()).willReturn(aTemperatureResponse());

        // when
        double actual = sensorDelegator.delegate("invalid-sensor-name");

        // then
    }

    @Test
    public void shouldReturnPressureValue() {
        // given
        given(dataConversionService.convertData()).willReturn(aPressureResponse());

        // when
        double actual = sensorDelegator.delegate(PRESSURE_SENSOR_NAME);

        // then
        assertThat(actual, is(21.0));
    }

    private TemperatureSensorResponse aTemperatureResponse() {
        return new TemperatureSensorResponse.Builder()
                .withTemperature(new MeasurementResult.Builder().withValue(21).build())
                .build();
    }

    private TemperatureSensorResponse aPressureResponse() {
        return new TemperatureSensorResponse.Builder()
                .withPressure(new MeasurementResult.Builder().withValue(21).build())
                .build();
    }

}