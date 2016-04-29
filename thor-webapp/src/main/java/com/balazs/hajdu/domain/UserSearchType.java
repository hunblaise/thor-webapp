package com.balazs.hajdu.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Balazs Hajdu
 */
public enum UserSearchType {

    LOCATION_BASED("location"),
    USERNAME_BASED("username"),
    KEYWORD_BASED("keyword");

    private static final Map<String, UserSearchType> ALIAS_MAPPING = new HashMap<>();

    static {
        for (UserSearchType userSearchType : UserSearchType.values()) {
            ALIAS_MAPPING.put(userSearchType.getValue(), userSearchType);
        }
    }

    public static Optional<UserSearchType> getUserSearchType(String value) {
        return Optional.ofNullable(ALIAS_MAPPING.get(value));
    }

    private final String value;

    UserSearchType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
