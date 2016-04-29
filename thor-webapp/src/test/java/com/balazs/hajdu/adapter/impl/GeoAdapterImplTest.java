package com.balazs.hajdu.adapter.impl;

import com.balazs.hajdu.components.transformers.GeocodedLocationTransformer;
import com.balazs.hajdu.components.transformers.UserLocationTransformer;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.domain.repository.maps.response.GeocodeResult;
import com.balazs.hajdu.domain.repository.maps.response.GoogleMapsGeocoding;
import com.balazs.hajdu.repository.GeoRepository;
import com.balazs.hajdu.repository.impl.GoogleMapsGeocodingRepository;
import com.google.common.collect.ImmutableList;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Balazs Hajdu
 */
public class GeoAdapterImplTest {

    private static final String TEST_IP_ADDRESS = "test-ip-address";
    private static final String TEST_ADDRESS = "test-address";
    private static final String TEST_STATUS = "test-status";
    private static final String TEST_FORMATTED_ADDRESS = "test-formatted-address";

    @Mock
    private GeoRepository geoRepository;

    @Mock
    private GoogleMapsGeocodingRepository geocodingRepository;

    @Mock
    private UserLocationTransformer userLocationTransformer;

    @Mock
    private GeocodedLocationTransformer geocodedLocationTransformer;

    @InjectMocks
    private GeoAdapterImpl geoAdapter;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnUserLocation() {
        // given
        Optional<CityResponse> cityResponse = Optional.of(aCityResponse());
        UserLocation userLocation = aUserLocation();
        // and
        given(geoRepository.getUserLocation(TEST_IP_ADDRESS)).willReturn(cityResponse);
        given(userLocationTransformer.transform(cityResponse)).willReturn(userLocation);

        // when
        UserLocation actual = geoAdapter.getUserLocation(TEST_IP_ADDRESS);

        // then
        verify(geoRepository, times(1)).getUserLocation(TEST_IP_ADDRESS);
        verify(userLocationTransformer, times(1)).transform(cityResponse);
        assertThat(actual, is(userLocation));
    }

    @Test
    public void shouldReturnGeocodeLocations() {
        // given
        GoogleMapsGeocoding googleMapsGeocoding = aGoogleMapsGeocoding();
        List<GeocodedLocation> geocodedLocations = geocodedLocations();
        // and
        given(geocodingRepository.convertAddressIntoCoordinates(TEST_ADDRESS)).willReturn(googleMapsGeocoding);
        given(geocodedLocationTransformer.transform(googleMapsGeocoding)).willReturn(geocodedLocations);

        // when
        List<GeocodedLocation> actual = geoAdapter.geocodeAddress(TEST_ADDRESS);

        // then
        verify(geocodingRepository, times(1)).convertAddressIntoCoordinates(TEST_ADDRESS);
        verify(geocodedLocationTransformer, times(1)).transform(googleMapsGeocoding);
        assertThat(actual, is(geocodedLocations));
    }

    private List<GeocodedLocation> geocodedLocations() {
        return ImmutableList.of(new GeocodedLocation.Builder().withFormattedLocation(TEST_FORMATTED_ADDRESS)
                .withCoordinates(new Coordinates.Builder().withLongitude(10.0)
                        .withLattitude(20.0)
                        .build())
                .build());
    }

    private GoogleMapsGeocoding aGoogleMapsGeocoding() {
        GoogleMapsGeocoding googleMapsGeocoding = new GoogleMapsGeocoding();

        googleMapsGeocoding.setStatus(TEST_STATUS);
        googleMapsGeocoding.setResults(googleGeocodingResults());

        return googleMapsGeocoding;
    }

    private List<GeocodeResult> googleGeocodingResults() {
        GeocodeResult geocodeResult = new GeocodeResult();

        geocodeResult.setAddressComponents(Collections.emptyList());
        geocodeResult.setFormattedAddress(TEST_FORMATTED_ADDRESS);

        return ImmutableList.of(geocodeResult);
    }

    private UserLocation aUserLocation() {
        return new UserLocation.Builder().withCountry("country")
                .withLongitude(1.0)
                .withLatitude(2.0)
                .withCity("city")
                .build();
    }

    private CityResponse aCityResponse() {
        Map<String, String> names = new HashMap<>();
        names.put("test", "test");
        return new CityResponse(new City(null, 1, 1, names), null, null, null, null, null, null, null, null, null);
    }


}