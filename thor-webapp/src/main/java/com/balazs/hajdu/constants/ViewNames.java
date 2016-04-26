package com.balazs.hajdu.constants;

/**
 * An enum to store the view names.
 *
 * @author Balazs Hajdu
 */
public enum  ViewNames {

    HOME_SIGNED_IN("home/homeSignedIn"),
    HOME_NOT_SIGNED_IN("home/homeNotSignedIn"),
    REGISTER("signup/signup"),
    SIGN_IN("signin/signin"),
    SENSORS("sensors/sensors"),
    MEASUREMENTS("measurements/measurements");

    private final String value;

    ViewNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
