package com.balazs.hajdu.client.service.impl;

import com.balazs.hajdu.client.domain.config.Bmp180Configuration;
import com.balazs.hajdu.client.repository.TemperatureRepository;
import com.balazs.hajdu.client.service.TemperatureService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Default implementation of {@link com.balazs.hajdu.client.service.TemperatureService}.
 *
 * @author Balazs Hajdu
 */
@Service
public class Bmp180TemperatureService implements TemperatureService {

    @Inject
    private TemperatureRepository temperatureRepository;

    @Override
    public double calculateTemperature() {
        int uncompensatedTemperatureData = temperatureRepository.readTemperature();
        Bmp180Configuration configuration = temperatureRepository.getConfiguration();

        int x1 = ((uncompensatedTemperatureData - configuration.getAc6()) * configuration.getAc5()) >> 15;
        int x2 = (configuration.getMc() << 11) / (x1 + configuration.getMd());
        int b5 = x1 + x2;

        return ((b5 + 8) >> 4) / 10;
    }

}
