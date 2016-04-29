package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.UserRoles;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.repository.maps.Coordinates;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An user related transformer.
 *
 * @author Balazs Hajdu
 */
@Component
public class UserTransformer {

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private GeocodedLocationTransformer geocodedLocationTransformer;

    /**
     * Transforms a Thor related domain object to database related domain object.
     *
     * @param user Thor related domain object
     * @param role user's role
     * @param instant creation time
     * @return databse related domain object
     */
    public UserEntity transformFrom(User user, String role, LocalDateTime instant) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setLocation(new GeoJsonPoint(user.getLocation().getCoordinates().getLat(), user.getLocation().getCoordinates().getLon()));
        userEntity.setAddress(userEntity.getAddress());

        userEntity.setCreated(instant);
        userEntity.setRole(role);

        userEntity.setSensors(Collections.emptyList());

        return userEntity;
    }

    /**
     * Transforms a database related domain object to Thor related domain object.
     *
     * @param userEntities database related domain object
     * @return Thor related domain objects
     */
    public List<User> transform(List<UserEntity> userEntities) {
        return userEntities.stream().map(this::transform).collect(Collectors.toList());
    }

    private User transform(UserEntity userEntity) {
        return new User.Builder().withUsername(userEntity.getUsername())
                .withPassword(userEntity.getPassword())
                .withLocation(new GeocodedLocation.Builder()
                        .withCoordinates(
                                new Coordinates.Builder()
                                        .withLattitude(userEntity.getLocation().getY())
                                        .withLongitude(userEntity.getLocation().getY())
                                        .build())
                        .withFormattedLocation(userEntity.getAddress())
                        .build())
                .withUserRole(UserRoles.getUserRoleByAlias(userEntity.getRole()).get())
                .build();
    }

}
