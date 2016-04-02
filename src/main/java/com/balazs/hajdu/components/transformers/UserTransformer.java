package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.view.RegisterForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Balazs Hajdu
 */
@Component
public class UserTransformer {

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private SensorTransformer sensorTransformer;

    public UserEntity transformFrom(User user, String role, LocalDateTime instant) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userEntity.setCreated(instant);
        userEntity.setRole(role);

        userEntity.setSensors(Arrays.asList(sensorTransformer.transform(new Sensor.Builder().withMeasurementResults(Collections.emptyList())
                .withSensorName("testName")
                .withLocation(46.404158, 20.308428)
                .build())));

        return userEntity;
    }

}
