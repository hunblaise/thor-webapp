package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.repository.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author Balazs Hajdu
 */
@Component
public class UserTransformer {

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private SensorFactory sensorFactory;

    @Inject
    private GeocodedLocationTransformer geocodedLocationTransformer;

    public UserEntity transformFrom(User user, String role, LocalDateTime instant) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setLocation(geocodedLocationTransformer.transform(user.getLocation()));

        userEntity.setCreated(instant);
        userEntity.setRole(role);

        userEntity.setSensors(Collections.emptyList());

        return userEntity;
    }

}
