package com.balazs.hajdu.domain.repository.maps.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * A POJO to store Google Map's geo coding's result.
 *
 * @author Balazs Hajdu
 */
public class AddressComponent {

    @JsonProperty(value = "long_name")
    private String longName;
    @JsonProperty(value = "short_name")
    private String shortName;
    private List<String> types;

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressComponent that = (AddressComponent) o;
        return Objects.equal(longName, that.longName) &&
                Objects.equal(shortName, that.shortName) &&
                Objects.equal(types, that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(longName, shortName, types);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("longName", longName)
                .add("shortName", shortName)
                .add("types", types)
                .toString();
    }
    // generated code ends here

}
