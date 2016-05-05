package com.balazs.hajdu.client.repository;

import com.balazs.hajdu.client.domain.request.MotionRequest;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * A repository to handle motion sensor related data.
 *
 * @author Balazs Hajdu
 */
@Repository
public class MotionSensorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotionSensorListener.class);

    @Inject
    private GpioPinDigitalInput motionSensor;

    @Inject
    private RestTemplate restTemplate;

    @Value("${SERVER_HOST}")
    private String serverHost;

    @Value("${SERVER_MOTION_SENSOR_PATH}")
    private String serverMotionSensorPath;

    @Value("${RPI_CLIENT_USERNAME}")
    private String username;

    @Value("${RPI_CLIENT_PASSWORD}")
    private String key;

    @PostConstruct
    private void initMotionDetection() {

        LOGGER.debug("Motion detection sensor listener initialized");

        motionSensor.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                LOGGER.error("Motion sensor digital state was changed. New state: {}", event.getState());

                if (event.getState().isHigh()) {
                    String serverUrl = buildUrl();
                    LOGGER.error("Caling Thor server with url: {}", serverUrl);

                    MotionRequest motionRequest = new MotionRequest();
                    motionRequest.setKey(key);

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<MotionRequest> request = new HttpEntity<>(motionRequest, httpHeaders);

                    ResponseEntity<MotionRequest> response = restTemplate.exchange(serverUrl, HttpMethod.POST, request, MotionRequest.class);

                    LOGGER.debug("Detected motion was sended to server. Response status: {}, Response body: {}", response.getStatusCode(), response.getBody());
                }
            }
        });
    }

    private String buildUrl() {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(serverHost)
                .path(String.format(serverMotionSensorPath, username))
                .build();

        return uriComponents.toUriString();
    }

}
