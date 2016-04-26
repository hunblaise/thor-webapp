package com.balazs.hajdu.repository;

import com.maxmind.geoip2.model.CityResponse;

import java.util.Optional;

/**
 * @author Balazs Hajdu
 */
public interface GeoRepository {

    Optional<CityResponse> getUserLocation(String ipAddress);

}
