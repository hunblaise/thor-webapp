package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * A controller for the home page.
 *
 * @author Balazs Hajdu
 */
@Controller
public class HomeController {

    /**
     * Controller method for the home page.
     *
     * @param principal represents the login id
     * @return view name based on the status of the user.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Principal principal) {
        return principal != null ? ViewNames.HOME_SIGNED_IN.getValue() : ViewNames.HOME_NOT_SIGNED_IN.getValue();
    }

}
