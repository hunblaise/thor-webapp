package com.balazs.hajdu.repository.impl;

import com.balazs.hajdu.domain.repository.maps.response.GoogleMapsGeocoding;
import com.balazs.hajdu.repository.GeoCodingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;

/**
 * @author Balazs Hajdu
 */
@Repository
public class GoogleMapsGeocodingRepository implements GeoCodingRepository {

    private static final String ADDRESS_PARAM = "address";
    private static final String KEY_PARAM = "key";

    @Value("${GOOGLE_MAPS_GEOCODING_API_URL}")
    private String serviceUrl;

    @Value("${GOOGLE_MAPS_GEOCODING_API_KEY}")
    private String serviceKey;

    @Inject
    private RestTemplate restTemplate;

    @Override
    public GoogleMapsGeocoding convertAddressIntoCoordinates(String address) {
        return restTemplate.getForObject(buildUrl(address), GoogleMapsGeocoding.class);
    }

    private String buildUrl(String address) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(2);
        parameters.add(ADDRESS_PARAM, address);
        parameters.add(KEY_PARAM, serviceKey);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(serviceUrl).queryParams(parameters).build();

        return uriComponents.toUriString();
    }

}
