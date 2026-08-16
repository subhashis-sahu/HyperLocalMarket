package com.example.hyperlocalmarket.model;



public class VerifyOtpRequest {

    private Long phNumber;
    private int otp;

    public VerifyOtpRequest(Long phNumber, int otp) {
        this.phNumber = phNumber;
        this.otp = otp;
    }

    public Long getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(Long phNumber) {
        this.phNumber = phNumber;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
