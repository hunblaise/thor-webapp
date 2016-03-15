package com.balazs.hajdu.components.factories;

import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.UserRoles;
import com.balazs.hajdu.domain.view.RegisterForm;
import org.springframework.stereotype.Component;

/**
 * @author Balazs Hajdu
 */
@Component
public class UserFactory {

    public User createFrom(RegisterForm registerForm) {
        return new User.Builder().withPassword(registerForm.getPassword())
                .withUsername(registerForm.getUsername())
                .withUserRole(UserRoles.USER)
                .build();
    }

}
