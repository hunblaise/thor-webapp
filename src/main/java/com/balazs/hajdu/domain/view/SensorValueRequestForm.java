package com.balazs.hajdu.domain.view;

import com.balazs.hajdu.domain.Sensor;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store the sensor's measurment values.
 *
 * @author Balazs Hajdu
 */
public class SensorValueRequestForm {

    SensorValue temperature;
    Sensor pressure;

    public SensorValue getTemperature() {
        return temperature;
    }

    public void setTemperature(SensorValue temperature) {
        this.temperature = temperature;
    }

    public Sensor getPressure() {
        return pressure;
    }

    public void setPressure(Sensor pressure) {
        this.pressure = pressure;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorValueRequestForm that = (SensorValueRequestForm) o;
        return Objects.equal(temperature, that.temperature) &&
                Objects.equal(pressure, that.pressure);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(temperature, pressure);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("temperature", temperature)
                .add("pressure", pressure)
                .toString();
    }
    // generated code ends here

}
