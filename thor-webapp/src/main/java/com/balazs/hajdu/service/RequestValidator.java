package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * A service to validate requests.
 *
 * @author Balazs Hajdu
 */
@Service
public class RequestValidator {

    @Inject
    private UserRepository userRepository;

    /**
     * Validate the user with password.
     *
     * @param username username
     * @param password password
     * @return true, if valid password
     */
    public boolean isValidUser(String username, String password) {
        UserEntity userEntity = userRepository.findOneByUsernameAndPassword(username, password);
        return userEntity != null;
    }

}
