package com.balazs.hajdu.service.impl;

import com.balazs.hajdu.components.factories.UserFactory;
import com.balazs.hajdu.components.transformers.UserTransformer;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.view.RegisterForm;
import com.balazs.hajdu.repository.UserRepository;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Default implementation of {@link com.balazs.hajdu.service.UserManagementService}.
 *
 * @author Balazs Hajdu
 */
@Service
public class UserManagementServiceImpl implements UserManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementServiceImpl.class);

    private static final String ROLE_USER = "ROLE_USER";

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserTransformer userTransformer;

    @Inject
    private UserFactory userFactory;

    @Inject
    private UserLocationService userLocationService;

    @Transactional
    @Override
    public User saveUser(RegisterForm registerForm) {
        User user = userFactory.createFrom(registerForm, userLocationService.geocodeLocation(registerForm.getAddress()));
        LOGGER.debug("Savig user to the database: {}", user);

        userRepository.save(userTransformer.transformFrom(user, ROLE_USER, LocalDateTime.now()));

        return user;
    }

}
