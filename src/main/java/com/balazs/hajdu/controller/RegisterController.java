package com.balazs.hajdu.controller;

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
 * @author Balazs Hajdu
 */
@Controller
public class RegisterController {

    private static final String REGISTER_VIEW_NAME = "signup/signup";
    private static final String SUCCESS_SIGN_UP = "signup.success";

    @Inject
    private UserManagementService userManagementService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.addObject(new RegisterForm());
        modelAndView.setViewName(REGISTER_VIEW_NAME);
        return modelAndView;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerNewUser(@Valid @ModelAttribute RegisterForm registerForm, Errors errors, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return REGISTER_VIEW_NAME;
        }

        User user = userManagementService.saveUser(registerForm);
        userService.signin(user);

        MessageHelper.addSuccessAttribute(ra, SUCCESS_SIGN_UP);

        return "redirect:/";
    }

}
