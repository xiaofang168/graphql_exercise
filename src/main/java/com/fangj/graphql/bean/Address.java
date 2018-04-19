package com.fangj.graphql.bean;

import java.io.Serializable;

/**
 * @author fangjie
 * @date Created in 上午10:20 18/4/19.
 */
public class Address implements Serializable {
    private String city;
    private String street;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
