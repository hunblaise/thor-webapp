package com.balazs.hajdu.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Balazs Hajdu
 */
public enum UserRoles {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private static final Map<String, UserRoles> ALIAS_MAPPING = new HashMap<>();

    static {
        for (UserRoles userRole : UserRoles.values()) {
            ALIAS_MAPPING.put(userRole.getAlias(), userRole);
        }
    }

    public static Optional<UserRoles> getUserRoleByAlias(String alias) {
        return Optional.ofNullable(ALIAS_MAPPING.get(alias));
    }

    private final String alias;

    UserRoles(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }


}
