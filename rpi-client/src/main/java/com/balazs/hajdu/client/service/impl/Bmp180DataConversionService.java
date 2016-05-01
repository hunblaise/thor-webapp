package com.balazs.hajdu.client.service.impl;

import com.balazs.hajdu.client.domain.config.Bmp180Configuration;
import com.balazs.hajdu.client.domain.config.Bmp180Mode;
import com.balazs.hajdu.client.domain.response.MeasurementResult;
import com.balazs.hajdu.client.domain.response.TemperatureSensorResponse;
import com.balazs.hajdu.client.repository.PressureRepository;
import com.balazs.hajdu.client.repository.TemperatureRepository;
import com.balazs.hajdu.client.service.DataConversionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Default implementation of {@link com.balazs.hajdu.client.service.DataConversionService}.
 *
 * @author Balazs Hajdu
 */
@Service
public class Bmp180DataConversionService implements DataConversionService {

    @Inject
    private TemperatureRepository temperatureRepository;

    @Inject
    private PressureRepository pressureRepository;

    @Inject
    private Bmp180Configuration configuration;

    @Override
    public TemperatureSensorResponse convertData() {
        int uncompensatedTemperature = temperatureRepository.readTemperature();
        int uncompensatedPressure = pressureRepository.readPressure();

        int x1 = ((uncompensatedTemperature - configuration.getAc6()) * configuration.getAc5()) >> 15;
        int x2 = (configuration.getMc() << 11) / (x1 + configuration.getMd());
        int b5 = x1 + x2;
        double temperature = ((b5 + 8) >> 4) / 10.0;

        int b6 = b5 - 4000;
        x1 = (configuration.getB2() * (b6 * b6) >> 12) >> 11;
        x2 = (configuration.getAc2() * b6) >> 11;
        int x3 = x1 + x2;
        int b3 = (((configuration.getAc1() * 4 + x3) << Bmp180Mode.ULTRA_HIGH_RESOLUTION.getOverSampleSettingValue()) + 2) / 4;

        x1 = (configuration.getAc3() * b6) >> 13;
        x2 = (configuration.getB1() * ((b6 * b6) >> 12)) >> 16;
        x3 = ((x1 + x2) + 2) >> 2;
        int b4 = (configuration.getAc4() * (x3 + 32768)) >> 15;
        int b7 = (uncompensatedPressure - b3) * (50000 >> Bmp180Mode.ULTRA_HIGH_RESOLUTION.getOverSampleSettingValue());

        int pressure = b7 < 0x80000000 ? (b7 * 2) / b4 : (b7 / b4) * 2;

        x1 = (pressure >> 8) * (pressure >> 8);
        x1 = (x1 * 3038) >> 16;
        x2 = (-7357 * pressure) >> 16;

        pressure = pressure + ((x1 + x2 + 3791) >> 4);

        return new TemperatureSensorResponse.Builder()
                .withTemperature(new MeasurementResult.Builder().withValue(temperature).build())
                .withPressure(new MeasurementResult.Builder().withValue(pressure).build())
                .build();
    }
}
