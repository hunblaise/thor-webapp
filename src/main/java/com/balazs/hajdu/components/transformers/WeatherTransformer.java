package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.repository.weather.CurrentWeather;
import com.balazs.hajdu.domain.repository.weather.Weather;
import org.springframework.stereotype.Component;

/**
 * Transforms OpenWeatherMap related domain objects to Thor related domain objects.
 *
 * @author Balazs Hajdu
 */
@Component
public class WeatherTransformer {

    /**
     * Transform to Thor related domain object.
     * @param currentWeather OpenWeatherMap related object
     * @return Thor related domain object
     */
    public Weather tranform(CurrentWeather currentWeather) {
        Weather.Builder builder = new Weather.Builder();

        if (!currentWeather.getWeather().isEmpty()) {
            builder.withDescription(currentWeather.getWeather().get(0).getDescription());
            builder.withIcon(currentWeather.getWeather().get(0).getIcon());
        }

        builder.withTemperature(currentWeather.getMain().getTemp());
        builder.withPressure(currentWeather.getMain().getPressure());
        builder.withHumidity(currentWeather.getMain().getHumidity());

        return builder.build();
    }

}
