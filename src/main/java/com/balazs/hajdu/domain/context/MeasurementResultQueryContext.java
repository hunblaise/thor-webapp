package com.balazs.hajdu.domain.context;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDateTime;

/**
 * An immutable POJO to store date based query params.
 *
 * @author Balazs Hajdu
 */
public final class MeasurementResultQueryContext {

    private final String username;
    private final String sensorName;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    private MeasurementResultQueryContext(Builder builder) {
        this.username = builder.username;
        this.sensorName = builder.sensorName;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public String getUsername() {
        return username;
    }

    public String getSensorName() {
        return sensorName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementResultQueryContext that = (MeasurementResultQueryContext) o;
        return Objects.equal(username, that.username) &&
                Objects.equal(sensorName, that.sensorName) &&
                Objects.equal(startDate, that.startDate) &&
                Objects.equal(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, sensorName, startDate, endDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("sensorName", sensorName)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .toString();
    }

    public static class Builder {

        private String username;
        private String sensorName;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withSensorName(String sensorName) {
            this.sensorName = sensorName;
            return this;
        }

        public Builder withStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public MeasurementResultQueryContext build() {
            return new MeasurementResultQueryContext(this);
        }

    }
    // generated code ends here

}
