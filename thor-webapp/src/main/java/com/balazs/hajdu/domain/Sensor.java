package com.balazs.hajdu.domain;

import com.balazs.hajdu.domain.response.MeasurementResultStatistics;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.Map;

/**
 * An immutable POJO to store sensor related data.
 *
 * @author Balazs Hajdu
 */
public final class Sensor {

    private final ObjectId id;
    private final String name;
    private final GeoJsonPoint location;
    private final double maxAlert;
    private final double minAlert;
    private final List<MeasurementResult> measurementResults;
    private final Map<StatisticsInterval, MeasurementResultStatistics> measurementResultStatistics;

    private Sensor(Builder builder) {
        this.id = builder.id;
        this.name = builder.sensorName;
        this.location = builder.location;
        this.maxAlert = builder.maxAlert;
        this.minAlert = builder.minAlert;
        this.measurementResults = builder.measurementResults;
        this.measurementResultStatistics = builder.measurementResultStatistics;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GeoJsonPoint getLocation() {
        return location;
    }

    public double getMaxAlert() {
        return maxAlert;
    }

    public double getMinAlert() {
        return minAlert;
    }

    public List<MeasurementResult> getMeasurementResults() {
        return measurementResults;
    }

    public Map<StatisticsInterval, MeasurementResultStatistics> getMeasurementResultStatistics() {
        return measurementResultStatistics;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Double.compare(sensor.maxAlert, maxAlert) == 0 &&
                Double.compare(sensor.minAlert, minAlert) == 0 &&
                Objects.equal(id, sensor.id) &&
                Objects.equal(name, sensor.name) &&
                Objects.equal(location, sensor.location) &&
                Objects.equal(measurementResults, sensor.measurementResults) &&
                Objects.equal(measurementResultStatistics, sensor.measurementResultStatistics);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, location, maxAlert, minAlert, measurementResults, measurementResultStatistics);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("location", location)
                .add("maxAlert", maxAlert)
                .add("minAlert", minAlert)
                .add("measurementResults", measurementResults)
                .add("measurementResultStatistics", measurementResultStatistics)
                .toString();
    }

    public static class Builder {

        private ObjectId id;
        private String sensorName;
        private GeoJsonPoint location;
        private double maxAlert;
        private double minAlert;
        private List<MeasurementResult> measurementResults;
        private Map<StatisticsInterval, MeasurementResultStatistics> measurementResultStatistics;

        public Builder withId(ObjectId id) {
            this.id = id;
            return this;
        }

        public Builder withSensorName(String sensorName) {
            this.sensorName = sensorName;
            return this;
        }

        public Builder withLocation(double latitude, double longitude) {
            this.location = new GeoJsonPoint(latitude, longitude);
            return this;
        }

        public Builder withMaxAlert(Double maxAlert) {
            this.maxAlert = maxAlert != null ? maxAlert : 0;
            return this;
        }

        public Builder withMinAlert(Double minAlert) {
            this.minAlert = minAlert != null ? minAlert : 0;
            return this;
        }

        public Builder withMeasurementResults(List<MeasurementResult> measurementResults) {
            this.measurementResults = measurementResults;
            return this;
        }

        public Builder withMeasurementResultStatistics(Map<StatisticsInterval, MeasurementResultStatistics> statistics) {
            this.measurementResultStatistics = statistics;
            return this;
        }

        public Sensor build() {
            return new Sensor(this);
        }
    }
    // generated code ends here
}
