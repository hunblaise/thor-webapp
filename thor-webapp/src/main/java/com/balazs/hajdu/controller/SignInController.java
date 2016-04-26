package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.ViewNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * A controller class for the sign in page.
 *
 * @author Balazs Hajdu
 */
@Controller
public class SignInController {

    /**
     * A method for the sign in page.
     *
     * @param modelAndView model and view
     * @return model and view
     */
    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public ModelAndView signin(ModelAndView modelAndView) {
        modelAndView.setViewName(ViewNames.SIGN_IN.getValue());

        return modelAndView;
    }

}
