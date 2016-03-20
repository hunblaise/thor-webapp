package com.balazs.hajdu.components.transformers;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.view.RegisterForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Balazs Hajdu
 */
@Component
public class UserTransformer {

    @Inject
    private PasswordEncoder passwordEncoder;

    public UserEntity transformFrom(User user, String role, LocalDateTime instant) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userEntity.setCreated(instant);
        userEntity.setRole(role);

        return userEntity;
    }

}
