package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.view.RegisterForm;

/**
 * @author Balazs Hajdu
 */
public interface UserManagementService {

    User saveUser(RegisterForm registerForm);

}
