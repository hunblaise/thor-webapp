package com.balazs.hajdu.facade.impl;

import com.balazs.hajdu.components.transformers.MeasurementResultTransformer;
import com.balazs.hajdu.domain.MeasurementResult;
import com.balazs.hajdu.domain.context.MeasurementResultQueryContext;
import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.forecast.FiveDayForecast;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.response.MeasurementResponse;
import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.balazs.hajdu.domain.response.WeatherSearchResponse;
import com.balazs.hajdu.domain.view.DateIntervalRequestForm;
import com.balazs.hajdu.domain.view.MeasurementResultRequestForm;
import com.balazs.hajdu.domain.view.WeatherSearchQueryForm;
import com.balazs.hajdu.facade.MeasurementResultFacade;
import com.balazs.hajdu.service.MeasurementResultService;
import com.balazs.hajdu.service.StatisticsService;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.WeatherService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
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

    @Override
    public MeasurementResult saveMeasurementResult(String userName, String sensorName, MeasurementResultRequestForm requestForm) {

        MeasurementResult result = new MeasurementResult.Builder().withValue(requestForm.getValue())
                .withLocation(requestForm.getLat(), requestForm.getLon())
                .withDate(LocalDateTime.now())
                .withUsername(userName)
                .withSensorName(sensorName)
                .build();

        return measurementResultService.saveMeasurementResultToSensor(result);
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

}
