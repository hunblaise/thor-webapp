package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.view.RegisterForm;
import com.balazs.hajdu.service.UserManagementService;
import com.balazs.hajdu.service.UserService;
import com.balazs.hajdu.support.web.MessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * A controller class for the registration page.
 *
 * @author Balazs Hajdu
 */
@Controller
public class RegisterController {

    private static final String SUCCESS_SIGN_UP = "signup.success";

    @Inject
    private UserManagementService userManagementService;

    @Inject
    private UserService userService;

    /**
     * A controller method for the registration page.
     *
     * @param modelAndView model and view
     * @return the page where the user will be redirected
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject(new RegisterForm());
        modelAndView.setViewName(ViewNames.REGISTER.getValue());
        return modelAndView;
    }

    /**
     * A method to handle the registration request.
     *
     * @param registerForm registration form
     * @param errors validation errors
     * @param ra redirect attributes
     * @return redirection value
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute RegisterForm registerForm, Errors errors, RedirectAttributes ra) {

        if (errors.hasErrors()) {
            return ViewNames.REGISTER.getValue();
        }

        User user = userManagementService.saveUser(registerForm);
        userService.signin(user);

        MessageHelper.addSuccessAttribute(ra, SUCCESS_SIGN_UP);

        return "redirect:/";
    }

}
