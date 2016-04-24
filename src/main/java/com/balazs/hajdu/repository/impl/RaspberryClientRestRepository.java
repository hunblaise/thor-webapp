package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.client.ClientMeasurementResult;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.repository.ClientRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * A repository to collect measurement results from the raspberry client.
 *
 * @author Balazs Hajdu
 */
@Repository
public class RaspberryClientRestRepository implements ClientRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaspberryClientRestRepository.class);

    private static final String KEY = "key";

    @Value("${RASPBERRY_CLIENT_HOST}")
    private String raspberryClientHost;

    @Value("${RASPBERRY_CLIENT_PATH}")
    private String raspberryClientPath;

    @Inject
    private RestTemplate restTemplate;

    @Override
    public MeasurementResult getMeasurementResultFromClient(String username, String password, SensorEntity sensor) {
        String url = buildUrl(username, password, sensor.getName());

        LOGGER.debug("Calling Raspberry Pi client with url: {}", url);

        ClientMeasurementResult result = restTemplate.getForObject(url, ClientMeasurementResult.class);

        return new MeasurementResult.Builder().withId(new ObjectId())
                .withValue(result.getValue())
                .withDate(LocalDateTime.now())
                .withUsername(username)
                .withSensorName(sensor.getName())
                .withLocation(sensor.getLocation().getX(), sensor.getLocation().getY())
                .build();
    }

    private String buildUrl(String username, String password, String sensorName) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add(KEY, password);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(raspberryClientHost)
                .path(String.format(raspberryClientPath, username, sensorName))
                .queryParams(parameters)
                .build();

        return uriComponents.toUriString();
    }
}
