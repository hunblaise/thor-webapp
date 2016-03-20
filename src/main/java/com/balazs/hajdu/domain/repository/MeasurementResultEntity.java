package com.balazs.hajdu.domain.repository;

import com.balazs.hajdu.domain.AbstractDocument;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDateTime;

/**
 * Representation of sensors' measurement results.
 *
 * @author Balazs Hajdu
 */
public class MeasurementResultEntity extends AbstractDocument {

    private LocalDateTime date;
    private Long value;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MeasurementResultEntity that = (MeasurementResultEntity) o;
        return Objects.equal(date, that.date) &&
                Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), date, value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("date", date)
                .add("value", value)
                .toString();
    }
    // generated code ends here

}
