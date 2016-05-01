package com.balazs.hajdu.client.domain.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Balazs Hajdu
 */
public enum SensorTypes {

    TEMPERATURE("temperature"),
    PRESSURE("pressure");

    private static final Map<String, SensorTypes> ALIAS_MAPPING = new HashMap<>();

    static {
        for (SensorTypes sensorType : SensorTypes.values()) {
            ALIAS_MAPPING.put(sensorType.getAlias(), sensorType);
        }
    }

    public static SensorTypes getSensorTypeByAlias(String alias) {
        return ALIAS_MAPPING.get(alias);
    }

    private final String alias;

    SensorTypes(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

}
