package com.ieti.map.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    private String title;
    private Address position;

    public Place(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Address getPosition() {
        return position;
    }

    public void setPosition(Address position) {
        this.position = position;
    }
}
