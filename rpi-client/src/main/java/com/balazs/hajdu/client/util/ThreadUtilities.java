package com.balazs.hajdu.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Balazs Hajdu
 */
public class ThreadUtilities {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtilities.class);

    public static void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("Thread sleep was interrupted: {}", e.getStackTrace());
        }
    }
}
