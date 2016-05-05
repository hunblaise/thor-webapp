package com.balazs.hajdu.facade.impl;

import com.balazs.hajdu.components.factories.SensorFactory;
import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.repository.forecast.FiveDayForecast;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.balazs.hajdu.domain.response.WeatherSearchResponse;
import com.balazs.hajdu.domain.view.DateIntervalRequestForm;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.WeatherSearchQueryForm;
import com.balazs.hajdu.facade.MeasurementResultFacade;
import com.balazs.hajdu.repository.ClientRepository;
import com.balazs.hajdu.repository.UserRepository;
import com.balazs.hajdu.service.MeasurementResultService;
import com.balazs.hajdu.service.StatisticsService;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.UserService;
import com.balazs.hajdu.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of {@link com.balazs.hajdu.facade.MeasurementResultFacade}.
 *
 * @author Balazs Hajdu
 */
@Component
public class MeasurementResultFacadeImpl implements MeasurementResultFacade {

    @Inject
    private MeasurementResultService measurementResultService;

    @Inject
    private MeasurementResultTransformer measurementResultTransformer;

    @Inject
    private UserLocationService userLocationService;

    @Inject
    private WeatherService weatherService;

    @Inject
    private StatisticsService statisticsService;

    @Inject
    private UserService  userService;

    @Inject
    private SensorFactory sensorFactory;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ClientRepository clientRepository;

    @Override
    public MeasurementResult saveMeasurementResult(String userName, String sensorName, Optional<MeasurementResultRequestForm> requestForm) {

        MeasurementResult.Builder builder = new MeasurementResult.Builder();

        builder.withId(new ObjectId());
        builder.withDate(LocalDateTime.now());
        builder.withUsername(userName);
        builder.withSensorName(sensorName);

        if (requestForm.isPresent()) {
            builder.withValue(requestForm.get().getValue());
            builder.withLocation(requestForm.get().getLat(), requestForm.get().getLon());
        } else {
            UserEntity entity = userRepository.findOneByUsername(userName);
            Map<User, List<Sensor>> user = userService.findUserByUsername(userName);
            builder.withLocation(entity.getLocation().getX(), entity.getLocation().getY());
        }

        return measurementResultService.saveMeasurementResultToSensor(builder.build());
    }

    @Override
    public List<MeasurementResponse> getAllMeasurementResultsForSensor(String username, String sensorName) {
        List<MeasurementResultEntity> measurementResults = measurementResultService.getMeasurementResultsByUsernameAndSensorName(username, sensorName);

        return measurementResultTransformer.transformToResponse(measurementResults);
    }

    @Override
    public List<MeasurementResponse> getMeasurementResultsFromDateInterval(String username, String sensorName, DateIntervalRequestForm requestForm) {
        MeasurementResultQueryContext context = new MeasurementResultQueryContext.Builder().withUsername(username)
                .withSensorName(sensorName)
                .withStartDate(requestForm.getStartDate())
                .withEndDate(requestForm.getEndDate())
                .build();

        return measurementResultTransformer.transformThorToResponse(
                measurementResultService.getLastsMeasurementResultsFromDateInterval(context));
    }

    @Override
    public Optional<WeatherSearchResponse> searchWeatherInLocation(WeatherSearchQueryForm weatherSearchQueryForm) {
        List<GeocodedLocation> geocodedLocations = userLocationService.geocodeLocation(weatherSearchQueryForm.getAddress());

        WeatherSearchResponse weatherSearchResponse = null;
        if (!geocodedLocations.isEmpty()) {
            GeocodedLocation geocodedLocation = geocodedLocations.get(0);

            FiveDayForecast fiveDayForecast = weatherService.getWeatherForecastForCity(geocodedLocation.getFormattedLocation());

            WeatherSearchResponse.Builder builder = new WeatherSearchResponse.Builder()
                    .withCity(geocodedLocation.getFormattedLocation())
                    .withDailyForecasts(fiveDayForecast.getDailyForecasts())
                    .withHourlyForecasts(fiveDayForecast.getHourlyForecasts());

            Optional<MeasurementResultStatistics> statistics = statisticsService.getStatisticsForLocation(geocodedLocation);
            if (statistics.isPresent()) {
                builder.withStatistics(statisticsService.getStatisticsForLocation(geocodedLocation).get());
            }

            weatherSearchResponse = builder.build();
        }

        return Optional.ofNullable(weatherSearchResponse);
    }

    @Override
    public MeasurementResponse updateSensorWithNewMeasurement(String username, String sensorName) {
        Map<User, List<Sensor>> users = userService.findUserByUsername(username);
        Optional<User> user = users.entrySet().stream()
                .filter(u -> u.getKey().getUsername().equals(username))
                .map(Map.Entry::getKey)
                .findFirst();

        MeasurementResponse.Builder builder = new MeasurementResponse.Builder();
        if (user.isPresent()) {
            List<Sensor> sensors = users.get(user.get());

            Optional<Sensor> sensor = sensors.stream()
                    .filter(s -> s.getName().equals(sensorName))
                    .findFirst();

            SensorEntity sensorEntity = sensor.isPresent() ? sensorFactory.transform(sensor.get()) : new SensorEntity();

            MeasurementResult measurementResult = clientRepository.getMeasurementResultFromClient(username, user.get().getPassword(), sensorEntity);
            measurementResultService.saveMeasurementResultToSensor(measurementResult);

            builder.withDate(measurementResult.getDate().format(DateTimeFormatter.ISO_DATE_TIME));
            builder.withValue(measurementResult.getValue());
        }

        return builder.build();
    }

}
