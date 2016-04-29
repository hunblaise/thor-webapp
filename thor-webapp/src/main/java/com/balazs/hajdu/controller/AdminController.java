package com.balazs.hajdu.controller;

import com.balazs.hajdu.components.delegators.UserSearchDelegator;
import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.view.AdminSearchQueryForm;
import com.balazs.hajdu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * A controller for the admin page.
 *
 * @author Balazs Hajdu
 */
@Controller
public class AdminController {

    @Inject
    private UserService userService;

    @Inject
    private UserSearchDelegator userSearchDelegator;

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.addObject("users", userService.findAllUser());
        modelAndView.setViewName(ViewNames.ADMIN.getValue());

        return modelAndView;
    }

    @RequestMapping(value = "/admin/search/{type}")
    public ModelAndView adminSearch(ModelAndView modelAndView,
                                    AdminSearchQueryForm adminSearchQueryForm,
                                    @PathVariable String type) {

        modelAndView.addObject("users", userSearchDelegator.delegateSearch(type, adminSearchQueryForm));
        modelAndView.setViewName(ViewNames.ADMIN.getValue());

        return modelAndView;
    }

}
