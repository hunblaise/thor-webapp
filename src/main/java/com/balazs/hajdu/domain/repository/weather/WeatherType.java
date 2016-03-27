package com.balazs.hajdu.domain.repository.weather;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * A POJO to store weather information.
 * The object structure based on the openweathermap's JSON contract.
 *
 * @author Balazs Hajdu
 */
public class WeatherType {

    Long id;
    String main;
    String description;
    String icon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherType that = (WeatherType) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(main, that.main) &&
                Objects.equal(description, that.description) &&
                Objects.equal(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, main, description, icon);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("main", main)
                .add("description", description)
                .add("icon", icon)
                .toString();
    }

}
