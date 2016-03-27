package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Transforms GeoIP2 related objects to thor related domain objects.
 *
 * @author Balazs Hajdu
 */
@Component
public class UserLocationTransformer {

    public UserLocation transform(Optional<CityResponse> cityResponse) {
        UserLocation.Builder builder = new UserLocation.Builder();

        if (cityResponse.isPresent()) {
            builder.withCountry(cityResponse.get().getCountry().getName());
            builder.withCity(cityResponse.get().getCity().getName());
            builder.withLatitude(cityResponse.get().getLocation().getLatitude());
            builder.withLongitude(cityResponse.get().getLocation().getLongitude());
        }

        return builder.build();
    }
}
