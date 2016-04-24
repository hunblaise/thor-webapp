package com.balazs.hajdu.client.controller;

import com.balazs.hajdu.client.account.Account;
import com.balazs.hajdu.client.account.AccountRepository;
import com.balazs.hajdu.client.delegator.SensorDelegator;
import com.balazs.hajdu.client.domain.request.MeasurementRequest;
import com.balazs.hajdu.client.domain.response.MeasurementResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Random;

/**
 * A controller to handle the REST API based communication with the server.
 *
 * @author Balazs Hajdu
 */
@RestController
public class MeasurementResultsController {

    public static final String INVALID_USER_ERROR_MESSAGE = "invalid user key";

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private SensorDelegator delegator;

    @RequestMapping(value = "/{username}/sensors/{sensorName}/get/actual", method = RequestMethod.GET)
    public MeasurementResult retrieveMeasurementResult(@PathVariable String username,
                                                       @PathVariable String sensorName,
                                                       MeasurementRequest measurementRequest) {
        Account account = accountRepository.getAccountByUsername(username);

        MeasurementResult.Builder builder = new MeasurementResult.Builder();
        if (account != null && account.getPassword().equalsIgnoreCase(measurementRequest.getKey())) {
            builder.withValue(delegator.delegate(sensorName));
        } else {
            builder.withError(INVALID_USER_ERROR_MESSAGE);
        }

        return builder.build();
    }
}
