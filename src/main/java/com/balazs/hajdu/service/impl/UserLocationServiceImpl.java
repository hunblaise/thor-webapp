package com.balazs.hajdu.service.impl;

import com.balazs.hajdu.adapter.GeoAdapter;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.service.UserLocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Default implementation for {@link com.balazs.hajdu.service.UserLocationService}.
 *
 * @author Balazs Hajdu
 */
@Service
public class UserLocationServiceImpl implements UserLocationService {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String TEST_ADDRESS = "0:0:0:0:0:0:0:1";

    @Value("${FALLBACK_IP_ADDRESS}")
    private String fallbackIpAddress;

    @Inject
    private GeoAdapter geoAdapter;

    @Override
    public UserLocation getUserLocation(String ipAddress) throws UnknownHostException {
        final UserLocation userLocation;

        if (LOCALHOST.equalsIgnoreCase(ipAddress) || TEST_ADDRESS.equalsIgnoreCase(ipAddress)) {
            userLocation = geoAdapter.getUserLocation(fallbackIpAddress);
        } else {
            userLocation = geoAdapter.getUserLocation(ipAddress);
        }

        return userLocation;
    }

}
