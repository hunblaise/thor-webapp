package com.balazs.hajdu.domain.view;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to encapsulate new sensor alerts.
 *
 * @author Balazs Hajdu
 */
public class SensorAlertRequestForm {

    private double maxAlertValue;
    private double minAlertValue;
    private String sensorAlert;

    public double getMaxAlertValue() {
        return maxAlertValue;
    }

    public void setMaxAlertValue(double maxAlertValue) {
        this.maxAlertValue = maxAlertValue;
    }

    public double getMinAlertValue() {
        return minAlertValue;
    }

    public void setMinAlertValue(double minAlertValue) {
        this.minAlertValue = minAlertValue;
    }

    public String getSensorAlert() {
        return sensorAlert;
    }

    public void setSensorAlert(String sensorAlert) {
        this.sensorAlert = sensorAlert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorAlertRequestForm that = (SensorAlertRequestForm) o;
        return Double.compare(that.maxAlertValue, maxAlertValue) == 0 &&
                Double.compare(that.minAlertValue, minAlertValue) == 0 &&
                Objects.equal(sensorAlert, that.sensorAlert);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maxAlertValue, minAlertValue, sensorAlert);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxAlertValue", maxAlertValue)
                .add("minAlertValue", minAlertValue)
                .add("sensorAlert", sensorAlert)
                .toString();
    }
}
