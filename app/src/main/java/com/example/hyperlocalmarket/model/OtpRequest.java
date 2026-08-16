package com.example.hyperlocalmarket.model;

public class OtpRequest {

    private Long phNumber;

    public OtpRequest(Long phNumber) {
        this.phNumber = phNumber;
    }

    public Long getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(Long phNumber) {
        this.phNumber = phNumber;
    }
}