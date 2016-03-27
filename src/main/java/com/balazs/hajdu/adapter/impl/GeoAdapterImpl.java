package com.balazs.hajdu.adapter.impl;

import com.balazs.hajdu.adapter.GeoAdapter;
import com.balazs.hajdu.components.transformers.UserLocationTransformer;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.repository.GeoRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Balazs Hajdu
 */
@Component
public class GeoAdapterImpl implements GeoAdapter {

    @Inject
    private GeoRepository geoRepository;

    @Inject
    private UserLocationTransformer userLocationTransformer;

    @Override
    public UserLocation getUserLocation(String ipAddress) {
        return userLocationTransformer.transform(geoRepository.getUserLocation(ipAddress));
    }
}
