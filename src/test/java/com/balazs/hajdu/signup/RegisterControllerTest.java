package com.balazs.hajdu.signup;

import com.balazs.hajdu.config.WebAppConfigurationAware;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RegisterControllerTest extends WebAppConfigurationAware {

    @Test
    public void displaysRegisterForm() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(model().attributeExists("registerForm"))
                .andExpect(view().name("signup/signup"))
                .andExpect(content().string(
                        allOf(
                                containsString("<title>Signup</title>"),
                                containsString("<legend>Please Sign Up</legend>")
                        ))
                );
    }

}