package com.balazs.hajdu.domain.repository.maps.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * A POJO to store Google Map's geo coding's result.
 *
 * @author Balazs Hajdu
 */
public class GoogleMapsGeocoding {


    private List<GeocodeResult> results;
    private String status;

    public List<GeocodeResult> getResults() {
        return results;
    }

    public void setResults(List<GeocodeResult> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleMapsGeocoding that = (GoogleMapsGeocoding) o;
        return Objects.equal(results, that.results) &&
                Objects.equal(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(results, status);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("results", results)
                .add("status", status)
                .toString();
    }
    // generated code ends here

}
