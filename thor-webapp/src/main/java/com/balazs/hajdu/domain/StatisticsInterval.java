package com.balazs.hajdu.domain;

/**
 * An enum to store statistics date interval information.
 *
 * @author Balazs Hajdu
 */
public enum StatisticsInterval {

    DAY(1, "last day"),
    WEEK(7, "last week"),
    MONTH(30, "last month");

    private final int interval;
    private final String description;

    StatisticsInterval(int interval, String description) {
        this.interval = interval;
        this.description = description;
    }

    public int getInterval() {
        return interval;
    }

    public String getDescription() {
        return description;
    }
}
