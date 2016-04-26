package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.repository.geo.UserLocation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Balazs Hajdu
 */
public class UserLocationTransformerTest {

    private UserLocationTransformer userLocationTransformer;

    @BeforeMethod
    public void setUp() throws Exception {
        userLocationTransformer = new UserLocationTransformer();
    }

    @Test
    public void shouldReturnEmptyUserLocationIfCityResponseEmpty() {
        // given
        UserLocation expected = new UserLocation.Builder().build();

        // when
        UserLocation actual = userLocationTransformer.transform(Optional.empty());

        // then
        assertThat(actual, is(expected));
    }
}