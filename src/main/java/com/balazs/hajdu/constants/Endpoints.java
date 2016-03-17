package com.balazs.hajdu.constants;

/**
 * Enum to store the endpoints' values.
 *
 * @author Balazs Hajdu
 */
public enum Endpoints {

    HOME_PAGE("/"),
    REGISTER_PAGE("register"),
    REGISTER_REQUEST("register"),
    SIGN_IN_PAGE("signin");

    private final String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
