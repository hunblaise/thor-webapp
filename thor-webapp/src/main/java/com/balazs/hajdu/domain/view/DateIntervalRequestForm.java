package com.balazs.hajdu.domain.view;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * A POJO to store date interval information.
 *
 * @author Balazs Hajdu
 */
public class DateIntervalRequestForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateIntervalRequestForm that = (DateIntervalRequestForm) o;
        return Objects.equal(startDate, that.startDate) &&
                Objects.equal(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(startDate, endDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startDate", startDate)
                .add("endDate", endDate)
                .toString();
    }

}
