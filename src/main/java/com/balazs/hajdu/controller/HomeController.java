package com.balazs.hajdu.controller;

import com.balazs.hajdu.adapter.WeatherAdapter;
import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.repository.weather.CurrentWeather;
import com.balazs.hajdu.domain.repository.weather.Weather;
import com.balazs.hajdu.repository.WeatherRepository;
import com.balazs.hajdu.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.security.Principal;

/**
 * A controller for the home page.
 *
 * @author Balazs Hajdu
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Inject
    private WeatherService weatherService;

    /**
     * Controller method for the home page.
     *
     * @param principal represents the login id
     * @return view name based on the status of the user.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Principal principal, ModelAndView modelAndView, HttpServletRequest request) {

        try {
            modelAndView.addObject("weather", weatherService.getCurrentWeather(request.getRemoteAddr()));
        } catch (UnknownHostException e) {
            LOGGER.warn("Can not retrieve user location for IP address {}", request.getRemoteUser());
        }

        if (principal != null) {
            modelAndView.setViewName(ViewNames.HOME_SIGNED_IN.getValue());
        } else {
            modelAndView.setViewName(ViewNames.HOME_NOT_SIGNED_IN.getValue());
        }

        return modelAndView;
    }

}
