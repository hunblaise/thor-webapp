package com.balazs.hajdu.adapter.impl;

import com.balazs.hajdu.adapter.GeoAdapter;
import com.balazs.hajdu.components.transformers.GeocodedLocationTransformer;
import com.balazs.hajdu.components.transformers.UserLocationTransformer;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.repository.GeoRepository;
import com.balazs.hajdu.repository.impl.GoogleMapsGeocodingRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Balazs Hajdu
 */
@Component
public class GeoAdapterImpl implements GeoAdapter {

    @Inject
    private GeoRepository geoRepository;

    @Inject
    private GoogleMapsGeocodingRepository geocodingRepository;

    @Inject
    private UserLocationTransformer userLocationTransformer;

    @Inject
    private GeocodedLocationTransformer geocodedLocationTransformer;

    @Override
    public UserLocation getUserLocation(String ipAddress) {
        return userLocationTransformer.transform(geoRepository.getUserLocation(ipAddress));
    }

    @Override
    public List<GeocodedLocation> geocodeAddress(String address) {
        return geocodedLocationTransformer.transform(geocodingRepository.convertAddressIntoCoordinates(address));
    }

}
