package com.balazs.hajdu.service;

import com.balazs.hajdu.adapter.GeoAdapter;
import com.balazs.hajdu.components.transformers.UserTransformer;
import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.UserRoles;
import com.balazs.hajdu.domain.repository.UserEntity;
import com.balazs.hajdu.domain.repository.maps.GeocodedLocation;
import com.balazs.hajdu.facade.SensorFacade;
import com.balazs.hajdu.repository.UserRepository;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A service for spring security's user authentication.
 *
 * @author Balazs Hajdu
 */
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserTransformer userTransformer;

    @Inject
    private SensorFacade sensorFacade;

    @Inject
    private GeoAdapter geoAdapter;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Load user by username: {}", username);

        Optional<UserEntity> user = Optional.ofNullable(userRepository.findOneByUsername(username));

        if (user.isPresent()) {
            LOGGER.debug("User was found: {}", user.get());
        }

        return buildUserDetails(user);
    }

    /**
     * Retrieve a user by username.
     *
     * @param username username
     * @return database related user object
     */
    public UserEntity getUserByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    /**
     * Retrieves every user in a given area.
     *
     * @param address address
     * @param radius radius
     * @return users, sensors, measurement results
     */
    public Map<User, List<Sensor>> findUsersInGivenArea(String address, int radius) {
        List<GeocodedLocation> geocodedLocations = geoAdapter.geocodeAddress(address);

        Map<User, List<Sensor>> usersMap;
        if (!geocodedLocations.isEmpty()) {
            usersMap = populateUserMap(userRepository.findUsersNearLocation(geocodedLocations.iterator().next().getCoordinates(), radius));
        } else {
            usersMap = Collections.emptyMap();
        }

        return usersMap;
    }

    /**
     * Retrieves users by a given keyword.
     *
     * @param keyword keyword
     * @return users
     */
    public Map<User, List<Sensor>> findUsersByKeyword(String keyword) {
        return populateUserMap(userRepository.findUsersByFullTextSearch(keyword));
    }

    /**
     * Retrieves users by a given username.
     *
     * @param username username
     * @return users
     */
    public Map<User, List<Sensor>> findUserByUsername(String username) {
        return populateUserMap(userTransformer.transform(ImmutableList.of(userRepository.findOneByUsername(username))));
    }

    /**
     * Retrieves every user from the database.
     *
     * @return every user from the database
     */
    public Map<User, List<Sensor>> findAllUser() {
        return populateUserMap(userTransformer.transform(userRepository.findAll()));
    }

    /**
     * Add the signed in user to the context.
     *
     * @param user user
     */
    public void signin(com.balazs.hajdu.domain.User user) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(user));
    }

    private Authentication authenticate(com.balazs.hajdu.domain.User user) {
        return new UsernamePasswordAuthenticationToken(createUser(user),
                null,
                getAuthorities(user.getUserRole().getAlias()));
    }

    private org.springframework.security.core.userdetails.User createUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getUserRole().getAlias()));
    }

    private UserDetails buildUserDetails(Optional<UserEntity> userEntity) {
        return userEntity.isPresent()
                ? new org.springframework.security.core.userdetails.User(userEntity.get().getUsername(),
                        userEntity.get().getPassword(), getAuthorities(userEntity.get().getRole()))
                : null;
    }

    private List<GrantedAuthority> getAuthorities(String userRoleAlias) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        Optional<UserRoles> userRoles = UserRoles.getUserRoleByAlias(userRoleAlias);
        if (userRoles.isPresent()) {
            authorities.add(new SimpleGrantedAuthority(userRoles.get().getAlias()));
        }

        return authorities;
    }

    private Map<User, List<Sensor>> populateUserMap(List<User> users) {
        return users.stream()
                .sorted((o1, o2) -> o1.getUsername().compareTo(o2.getUsername()))
                .collect(Collectors.toMap(Function.identity(),
                        user -> sensorFacade.getAllSensorByUsername(user.getUsername()),
                        (user1, user2) -> user1,
                        TreeMap::new));
    }

}
