package com.balazs.hajdu.adapter;

import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;

import java.util.List;

/**
 * An adapter for the geo repositories.
 *
 * @author Balazs Hajdu
 */
public interface GeoAdapter {

    /**
     * Retrieves the user's location.
     *
     * @param ipAddress ip address
     * @return the user's location
     */
    UserLocation getUserLocation(String ipAddress);

    /**
     * Converts address to geographical coordinates.
     *
     * @param address address
     * @return geographical coordinates
     */
    List<GeocodedLocation> geocodeAddress(String address);

}
