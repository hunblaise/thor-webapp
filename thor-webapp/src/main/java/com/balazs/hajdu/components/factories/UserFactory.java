package com.balazs.hajdu.components.factories;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.UserRoles;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.domain.view.RegisterForm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Creates User objects.
 *
 * @author Balazs Hajdu
 */
@Component
public class UserFactory {

    public User createFrom(RegisterForm registerForm, List<GeocodedLocation> geocodedLocations) {

        User.Builder builder = new User.Builder().withPassword(registerForm.getPassword())
                .withUsername(registerForm.getUsername())
                .withUserRole(UserRoles.USER);

        if (!geocodedLocations.isEmpty()) {
            builder.withLocation(geocodedLocations.get(0));
        }

        return builder.build();
    }

}
