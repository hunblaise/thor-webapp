package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.repository.LocationEntity;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.domain.repository.maps.response.GeocodeResult;
import com.balazs.hajdu.domain.repository.maps.response.Geometry;
import com.balazs.hajdu.domain.repository.maps.response.GoogleMapsGeocoding;
import com.google.common.collect.ImmutableList;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class GeocodedLocationTransformerTest {

    private static final String TEST_FORMATTED_LOCATION = "test-formatted-location";
    private static final double TEST_LATITUDE = 16;
    private static final double TEST_LONGITUDE = 23;
    private GeocodedLocationTransformer geocodedLocationTransformer;

    @BeforeMethod
    public void setUp() throws Exception {
        geocodedLocationTransformer = new GeocodedLocationTransformer();
    }

    @Test
    public void shouldTransformGoogleMapsGeocodedData() {
        // given
        List<GeocodedLocation> expected = ImmutableList.of(aGeocodedLocation());

        // when
        List<GeocodedLocation> actual = geocodedLocationTransformer.transform(googleMapsGeocodedData());

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldTransformGeocodedObjectsToDatabaseObjects() {
        // given
        LocationEntity expected = aLocationEntity();

        // when
        LocationEntity actual = geocodedLocationTransformer.transform(aGeocodedLocation());

        // then
        assertThat(actual, is(expected));
    }

    private LocationEntity aLocationEntity() {
        LocationEntity locationEntity = new LocationEntity();

        locationEntity.setAddress(TEST_FORMATTED_LOCATION);
        locationEntity.setLocation(new GeoJsonPoint(TEST_LATITUDE, TEST_LONGITUDE));

        return locationEntity;
    }

    private GoogleMapsGeocoding googleMapsGeocodedData() {
        GoogleMapsGeocoding googleMapsGeocoding = new GoogleMapsGeocoding();
        googleMapsGeocoding.setResults(geocodedResults());
        return googleMapsGeocoding;
    }

    private List<GeocodeResult> geocodedResults() {
        GeocodeResult geocodeResult = new GeocodeResult();
        geocodeResult.setFormattedAddress(TEST_FORMATTED_LOCATION);

        com.balazs.hajdu.domain.repository.maps.response.Location location = new com.balazs.hajdu.domain.repository.maps.response.Location();
        location.setLat(TEST_LATITUDE);
        location.setLng(TEST_LONGITUDE);

        Geometry geometry = new Geometry();
        geometry.setLocation(location);

        geocodeResult.setGeometry(geometry);
        return ImmutableList.of(geocodeResult);
    }

    private GeocodedLocation aGeocodedLocation() {
        return new GeocodedLocation.Builder()
                .withFormattedLocation(TEST_FORMATTED_LOCATION)
                .withCoordinates(new Coordinates.Builder()
                        .withLattitude(TEST_LATITUDE)
                        .withLongitude(TEST_LONGITUDE)
                        .build())
                .build();
    }
}