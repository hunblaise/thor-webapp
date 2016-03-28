package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.repository.geo.UserLocation;

import java.net.UnknownHostException;

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

}
