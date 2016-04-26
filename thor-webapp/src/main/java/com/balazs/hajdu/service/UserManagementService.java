package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.view.RegisterForm;

/**
 * A service for saving, removing and modifying users' data.
 *
 * @author Balazs Hajdu
 */
public interface UserManagementService {

    /**
     * Save a user to the database.
     * @param registerForm register form
     * @return the saved user
     */
    User saveUser(RegisterForm registerForm);

}
