package com.example.hyperlocalmarket.model;

public class Profile {

    private Long phNumber;
    private String name;

    public Profile() {
    }

    public Profile(Long phNumber, String name) {
        this.phNumber = phNumber;
        this.name = name;
    }

    public Long getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(Long phNumber) {
        this.phNumber = phNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
