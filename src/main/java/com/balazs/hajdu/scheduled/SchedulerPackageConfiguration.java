package com.balazs.hajdu.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Balazs Hajdu
 */
@Configuration
public class SchedulerPackageConfiguration {

    @Bean
    public ScheduledExecutorService backgroundTaskScheduler() {
        return Executors.newScheduledThreadPool(java.lang.Runtime.getRuntime().availableProcessors());
    }

}
