package com.balazs.hajdu.client.controller;

import com.balazs.hajdu.client.account.Account;
import com.balazs.hajdu.client.account.AccountRepository;
import com.balazs.hajdu.client.delegator.SensorDelegator;
import com.balazs.hajdu.client.domain.request.MeasurementRequest;
import com.balazs.hajdu.client.domain.response.MeasurementResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * @author Balazs Hajdu
 */
public class MeasurementResultsControllerTest {

    private static final String TEST_USERNAME = "test-username";
    private static final String TEST_PASSWORD = "test-password";
    private static final String TEST_SENSOR_NAME = "test-sensor-name";
    private static final String TEST_KEY = "test-password";

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private SensorDelegator sensorDelegator;

    @InjectMocks
    private MeasurementResultsController measurementResultsController;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnMeasurementResultWithValueIfUserIsFoundAndTheKeyWasCorrect() {
        // given
        given(accountRepository.getAccountByUsername(TEST_USERNAME)).willReturn(new Account(TEST_USERNAME, TEST_PASSWORD));
        given(sensorDelegator.delegate(TEST_SENSOR_NAME)).willReturn(1.0);
        // and
        MeasurementRequest request = new MeasurementRequest();
        request.setKey(TEST_KEY);

        // when
        MeasurementResult actual = measurementResultsController.retrieveMeasurementResult(TEST_USERNAME, TEST_SENSOR_NAME, request);

        // then
        assertThat(actual.getValue(), is(1.0));
        assertThat(actual.getError(), is(nullValue()));
    }

    @Test
    public void shouldReturnErrorMessageIfUserWasNotFoundInTheDatabase() {
        // given
        given(accountRepository.getAccountByUsername(TEST_USERNAME)).willReturn(null);
        given(sensorDelegator.delegate(TEST_SENSOR_NAME)).willReturn(1.0);
        // and
        MeasurementRequest request = new MeasurementRequest();
        request.setKey(TEST_KEY);

        // when
        MeasurementResult actual = measurementResultsController.retrieveMeasurementResult(TEST_USERNAME, TEST_SENSOR_NAME, request);

        // then
        assertThat(actual.getValue(), is(0.0));
        assertThat(actual.getError(), is(MeasurementResultsController.INVALID_USER_ERROR_MESSAGE));
    }

    @Test
    public void shouldReturnErrorMessageIfUserKeyIsInvalid() {
        // given
        given(accountRepository.getAccountByUsername(TEST_USERNAME)).willReturn(new Account(TEST_USERNAME, TEST_PASSWORD));
        given(sensorDelegator.delegate(TEST_SENSOR_NAME)).willReturn(1.0);
        // and
        MeasurementRequest request = new MeasurementRequest();
        request.setKey("invalid-key");

        // when
        MeasurementResult actual = measurementResultsController.retrieveMeasurementResult(TEST_USERNAME, TEST_SENSOR_NAME, request);

        // then
        assertThat(actual.getValue(), is(0.0));
        assertThat(actual.getError(), is(MeasurementResultsController.INVALID_USER_ERROR_MESSAGE));
    }

}