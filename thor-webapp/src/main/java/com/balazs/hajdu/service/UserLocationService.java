package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;

import java.net.UnknownHostException;
import java.util.List;

/**
 * A service to retrieve the user's location.
 *
 * @author Balazs Hajdu
 */
public interface UserLocationService {

    /**
     * Retrieve the user's location.
     *
     * @param ipAddress ip address
     * @return user location
     */
    UserLocation getUserLocation(String ipAddress) throws UnknownHostException;

    /**
     * Geocode an address and returns the result.
     *
     * @param address address
     * @return geocoded location
     */
    List<GeocodedLocation> geocodeLocation(String address);

}
