package com.balazs.hajdu.service;

import com.balazs.hajdu.domain.UserRoles;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Balazs Hajdu
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Load user by username: {}", username);

        Optional<UserEntity> user = Optional.ofNullable(userRepository.findOneByUsername(username));

        if (user.isPresent()) {
            LOGGER.debug("User was found: {}", user.get());
        }

        return buildUserDetails(user);
    }

    public void signin(com.balazs.hajdu.domain.User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(com.balazs.hajdu.domain.User user) {
        return new UsernamePasswordAuthenticationToken(createUser(user), null, getAuthorities(user.getUserRole().getAlias()));
    }

    private User createUser(com.balazs.hajdu.domain.User user) {
        return new User(user.getUsername(), user.getPassword(), getAuthorities(user.getUserRole().getAlias()));
    }

    private UserDetails buildUserDetails(Optional<UserEntity> userEntity) {
        return userEntity.isPresent() ? new User(userEntity.get().getUsername(),
                userEntity.get().getPassword(), getAuthorities(userEntity.get().getRole())) : null;
    }

    private List<GrantedAuthority> getAuthorities(String userRoleAlias) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        Optional<UserRoles> userRoles = UserRoles.getUserRoleByAlias(userRoleAlias);
        if (userRoles.isPresent()) {
            authorities.add(new SimpleGrantedAuthority(userRoles.get().getAlias()));
        }

        return authorities;
    }

}
