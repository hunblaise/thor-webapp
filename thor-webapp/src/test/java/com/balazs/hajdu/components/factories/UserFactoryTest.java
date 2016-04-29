package com.balazs.hajdu.components.factories;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.UserRoles;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.domain.view.RegisterForm;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class UserFactoryTest {

    private static final String TEST_ADDRESS = "test-address";
    private static final String TEST_USERNAME = "test-username";
    private static final String TEST_PASSWORD = "test-password";
    private static final double TEST_LATITUDE = 23;
    private static final double TEST_LONGITUDE = 16;
    private static final String TEST_FORMATTED_LOCATION = "test-formatted-location";
    private UserFactory userFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        userFactory = new UserFactory();
    }

    @Test
    public void shouldCreateUserWithLocation() {
        // given
        User expected = userWithLocation();

        // when
        User actual = userFactory.createFrom(aRegisterForm(), geocodedLocations());

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldCreateUserWithoutLocation() {
        // given
        User expected = userWithoutLocation();

        // when
        User actual = userFactory.createFrom(aRegisterForm(), Collections.emptyList());

        // then
        assertThat(actual.getLocation(), is(nullValue()));
        assertThat(actual, is(expected));
    }

    private User userWithoutLocation() {
        return new User.Builder().withUserRole(UserRoles.USER)
                .withPassword(TEST_PASSWORD)
                .withUsername(TEST_USERNAME)
                .build();
    }

    private User userWithLocation() {
        return new User.Builder().withUserRole(UserRoles.USER)
                .withPassword(TEST_PASSWORD)
                .withUsername(TEST_USERNAME)
                .withLocation(geocodedLocations().get(0))
                .build();
    }

    private List<GeocodedLocation> geocodedLocations() {
        return ImmutableList.of(new GeocodedLocation.Builder()
                .withCoordinates(new Coordinates.Builder().withLattitude(TEST_LATITUDE)
                        .withLongitude(TEST_LONGITUDE)
                        .build())
                .withFormattedLocation(TEST_FORMATTED_LOCATION)
                .build());
    }

    private RegisterForm aRegisterForm() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setAddress(TEST_ADDRESS);
        registerForm.setUsername(TEST_USERNAME);
        registerForm.setPassword(TEST_PASSWORD);
        return registerForm;
    }
}