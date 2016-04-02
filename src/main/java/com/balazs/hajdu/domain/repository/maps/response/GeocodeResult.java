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
public class GeocodeResult {

    @JsonProperty(value = "address_components")
    private List<AddressComponent> addressComponents;
    @JsonProperty(value = "formatted_address")
    private String formattedAddress;
    private Geometry geometry;
    @JsonProperty(value = "place_id")
    private String placeId;
    private List<String> types;

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
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
        GeocodeResult that = (GeocodeResult) o;
        return Objects.equal(addressComponents, that.addressComponents) &&
                Objects.equal(formattedAddress, that.formattedAddress) &&
                Objects.equal(geometry, that.geometry) &&
                Objects.equal(placeId, that.placeId) &&
                Objects.equal(types, that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(addressComponents, formattedAddress, geometry, placeId, types);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("addressComponents", addressComponents)
                .add("formattedAddress", formattedAddress)
                .add("geometry", geometry)
                .add("placeId", placeId)
                .add("types", types)
                .toString();
    }
    // generated code ends here

}
