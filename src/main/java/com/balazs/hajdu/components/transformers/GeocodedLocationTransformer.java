package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.repository.LocationEntity;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.repository.maps.Location;
import com.balazs.hajdu.domain.repository.maps.response.GeocodeResult;
import com.balazs.hajdu.domain.repository.maps.response.GoogleMapsGeocoding;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Transforms Google Maps related domain objects to Thor related domain objects.
 *
 * @author Balazs Hajdu
 */
@Component
public class GeocodedLocationTransformer {

    /**
     * Transforms Google Maps related domain objects to Thor related domain objects.
     *
     * @param googleMapsGeocoding google maps related domain object
     * @return Thor related domain object
     */
    public List<GeocodedLocation> transform(GoogleMapsGeocoding googleMapsGeocoding) {
        return googleMapsGeocoding.getResults().stream()
                .map(geocodeResult -> new GeocodedLocation.Builder()
                        .withFormattedLocation(geocodeResult.getFormattedAddress())
                        .withLocation(transformLocation(geocodeResult))
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Transforms Thor related domain object to database related domain object.
     *
     * @param geocodedLocation Thor related domain object
     * @return database related domain object
     */
    public LocationEntity transform(GeocodedLocation geocodedLocation) {
        LocationEntity locationEntity = new LocationEntity();

        locationEntity.setAddress(geocodedLocation.getFormattedLocation());
        locationEntity.setLat(geocodedLocation.getLocation().getLat());
        locationEntity.setLon(geocodedLocation.getLocation().getLon());

        return locationEntity;
    }

    private Location transformLocation(GeocodeResult geocodeResult) {
        return new Location.Builder()
                .withLattitude(geocodeResult.getGeometry().getLocation().getLat())
                .withLongitude(geocodeResult.getGeometry().getLocation().getLng())
                .build();
    }

}
