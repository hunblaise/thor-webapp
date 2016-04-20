package com.balazs.hajdu.components.factories;

import com.balazs.hajdu.domain.repository.forecast.Forecast;
import com.balazs.hajdu.domain.repository.forecast.ForecastDetail;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastInformation;
import com.balazs.hajdu.domain.repository.forecast.response.ForecastResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A factory to create weather forecast object.
 *
 * @author Balazs Hajdu
 */
@Component
public class ForecastFactory {

    public Forecast createForecastFrom(ForecastResponse forecastResponse) {
        List<ForecastDetail> forecastDetails = forecastResponse.getList().stream()
                .map(this::createForecastDetailFrom)
                .collect(Collectors.toList());

        return new Forecast.Builder().withDetails(forecastDetails).build();
    }

    private ForecastDetail createForecastDetailFrom(ForecastInformation forecastInformation) {
        ForecastDetail.Builder builder = new ForecastDetail.Builder().withTemperature(forecastInformation.getMain().getTemp())
                .withMaxTemperature(forecastInformation.getMain().getTempMax())
                .withMinTemperature(forecastInformation.getMain().getTempMin())
                .withHumidity(forecastInformation.getMain().getHumidity())
                .withPressure(forecastInformation.getMain().getPressure())
                .withDate(forecastInformation.getDate());

        if (!forecastInformation.getWeather().isEmpty()) {
            builder.withDescription(forecastInformation.getWeather().iterator().next().getDescription());
            builder.withIcon(forecastInformation.getWeather().iterator().next().getIcon());
            builder.withId(forecastInformation.getWeather().iterator().next().getId());
        }

        return builder.build();
    }

}
