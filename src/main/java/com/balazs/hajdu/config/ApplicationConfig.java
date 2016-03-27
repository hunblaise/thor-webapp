package com.balazs.hajdu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.balazs.hajdu.Application;

/**
 * Main configuration class.
 * @author Balazs Hajdu
 */
@Configuration
@PropertySource("classpath:persistence.properties")
@PropertySource("classpath:application.properties")
@PropertySource("classpath:db.properties")
@PropertySource("classpath:openweathermap.properties")
@ComponentScan(basePackageClasses = Application.class)
class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}