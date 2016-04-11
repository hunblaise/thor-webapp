package com.balazs.hajdu.constants;

/**
 * Enum to store the endpoints' values.
 *
 * @author Balazs Hajdu
 */
public enum Endpoints {

    HOME_PAGE("/", "/"),
    REGISTER_PAGE("register", "/register"),
    SIGN_IN_PAGE("signin", "/signin"),
    SENSORS("sensors", "/sensors");

    private final String endpoint;
    private final String relativeEndpoint;

    Endpoints(String endpoint, String relativeEndpoint) {
        this.endpoint = endpoint;
        this.relativeEndpoint = relativeEndpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getRelativeEndpoint() {
        return relativeEndpoint;
    }

}
