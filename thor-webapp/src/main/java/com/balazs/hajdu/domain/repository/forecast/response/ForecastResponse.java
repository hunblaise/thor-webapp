package com.balazs.hajdu.domain.repository.forecast.response;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * A POJO to store weather forecast information.
 * openweathermap's JSON contract:
 * http://openweathermap.org/forecast5
 *
 * @author Balazs Hajdu
 */
public class ForecastResponse {

    private String cod;
    private Double message;
    private Integer cnt;
    private CityInformation city;
    private List<ForecastInformation> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public CityInformation getCity() {
        return city;
    }

    public void setCity(CityInformation city) {
        this.city = city;
    }

    public List<ForecastInformation> getList() {
        return list;
    }

    public void setList(List<ForecastInformation> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastResponse that = (ForecastResponse) o;
        return Objects.equal(cod, that.cod) &&
                Objects.equal(message, that.message) &&
                Objects.equal(cnt, that.cnt) &&
                Objects.equal(city, that.city) &&
                Objects.equal(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cod, message, cnt, city, list);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cod", cod)
                .add("message", message)
                .add("cnt", cnt)
                .add("city", city)
                .add("list", list)
                .toString();
    }

}
