package com.balazs.hajdu.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * An immutable POJO to store measurement results.
 *
 * @author Balazs Hajdu
 */
public final class MeasurementResult {

    private final ObjectId id;
    private final LocalDateTime date;
    private final Long value;

    private MeasurementResult(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
        this.value = builder.value;
    }

    public ObjectId getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getValue() {
        return value;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementResult that = (MeasurementResult) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(date, that.date) &&
                Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, date, value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("date", date)
                .add("value", value)
                .toString();
    }

    public static class Builder {

        private ObjectId id;
        private LocalDateTime date;
        private Long value;

        public Builder withId(ObjectId id) {
            this.id = id;
            return this;
        }

        public Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withValue(Long value) {
            this.value = value;
            return this;
        }
    }
    // generated code ends here

}
