package com.balazs.hajdu.client.domain.config;

/**
 * @author Balazs Hajdu
 */
public enum Bmp180Mode {

    ULTRA_LOW_POWER(0, 5),
    STANDARD(1, 8),
    HIGH_RESOLUTION(2, 14),
    ULTRA_HIGH_RESOLUTION(3, 26);

    private final int overSampleSettingValue;
    private final int minimumConversionTime;

    Bmp180Mode(int overSampleSettingValue, int minimumConversionTime) {
        this.overSampleSettingValue = overSampleSettingValue;
        this.minimumConversionTime = minimumConversionTime;
    }

    public int getOverSampleSettingValue() {
        return overSampleSettingValue;
    }

    public int getMinimumConversionTime() {
        return minimumConversionTime;
    }
}
