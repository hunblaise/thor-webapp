package com.balazs.hajdu.controller;

import com.balazs.hajdu.constants.ViewNames;
import com.balazs.hajdu.domain.repository.forecast.HourlyForecast;
import com.balazs.hajdu.domain.repository.geo.UserLocation;
import com.balazs.hajdu.domain.response.WeatherSearchResponse;
import com.balazs.hajdu.domain.view.WeatherSearchQueryForm;
import com.balazs.hajdu.error.exceptions.InvalidDatabaseOperationException;
import com.balazs.hajdu.facade.MeasurementResultFacade;
import com.balazs.hajdu.service.UserLocationService;
import com.balazs.hajdu.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A controller for the home page.
 *
 * @author Balazs Hajdu
 */
@RestController
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Inject
    private WeatherService weatherService;

    @Inject
    private UserLocationService userLocationService;

    @Inject
    private MeasurementResultFacade measurementResultFacade;

    /**
     * Controller method for the home page.
     *
     * @param principal represents the login id
     * @return view name based on the status of the user.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Principal principal, ModelAndView modelAndView, HttpServletRequest request) {
        try {
            UserLocation location = userLocationService.getUserLocation(request.getRemoteAddr());

            modelAndView.addObject("weather", weatherService.getCurrentWeather(location.getCity()));
            modelAndView.addObject("forecast",  weatherService.getWeatherForecastForCity(location.getCity()));
            modelAndView.addObject("location", userLocationService.geocodeLocation(location.getCity()));
        } catch (UnknownHostException e) {
            LOGGER.error("Can not retrieve user location for IP address {}", request.getRemoteUser());
        }

        if (principal != null) {
            modelAndView.setViewName(ViewNames.HOME_SIGNED_IN.getValue());
        } else {
            modelAndView.setViewName(ViewNames.HOME_NOT_SIGNED_IN.getValue());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/weather/forecast/hourly", method = RequestMethod.GET)
    public List<HourlyForecast> getHourlyForecast(HttpServletRequest request) {
        List<HourlyForecast> hourlyForecast = new ArrayList<>();
        try {
            UserLocation location = userLocationService.getUserLocation(request.getRemoteAddr());

            hourlyForecast = weatherService.getWeatherForecastForCity(location.getCity()).getHourlyForecasts();
        } catch (UnknownHostException e) {
            LOGGER.error("Can not retrieve user location for IP address {}", request.getRemoteUser());
        }

        return hourlyForecast;
    }

    @RequestMapping(value = "/weather/search", method = RequestMethod.GET)
    public WeatherSearchResponse getWeatherOfCity(WeatherSearchQueryForm weatherSearchQueryForm) throws InvalidDatabaseOperationException {
        return measurementResultFacade.searchWeatherInLocation(weatherSearchQueryForm)
                .orElseThrow(() -> new InvalidDatabaseOperationException("Could not find weather information for location"));
    }

}
