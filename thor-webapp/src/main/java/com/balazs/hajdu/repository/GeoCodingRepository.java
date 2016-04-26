package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.repository.maps.response.GoogleMapsGeocoding;

/**
 * An interface to convert addresses into geographic coordinates.
 *
 * @author Balazs Hajdu
 */
public interface GeoCodingRepository {

    GoogleMapsGeocoding convertAddressIntoCoordinates(String address);

}
