package com.balazs.hajdu.domain.view;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Register form.
 *
 * @author Balazs Hajdu
 */
public class RegisterForm {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = RegisterForm.NOT_BLANK_MESSAGE)
    private String username;

    @NotBlank(message = RegisterForm.NOT_BLANK_MESSAGE)
    private String password;

    @NotBlank(message = RegisterForm.NOT_BLANK_MESSAGE)
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
