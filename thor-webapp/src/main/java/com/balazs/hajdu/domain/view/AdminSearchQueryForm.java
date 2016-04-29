package com.balazs.hajdu.domain.view;

/**
 * A simple POJO to store admin page search related data.
 *
 * @author Balazs Hajdu
 */
public class AdminSearchQueryForm {

    private String keyword;
    private String address;
    private String username;
    private Integer distance;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
