package com.example.hyperlocalmarket.api;

import com.example.hyperlocalmarket.model.AuthResponse;
import com.example.hyperlocalmarket.model.CreateListingRequest;
import com.example.hyperlocalmarket.model.MessageResponse;
import com.example.hyperlocalmarket.model.OtpRequest;

import com.example.hyperlocalmarket.model.Product;
import com.example.hyperlocalmarket.model.VerifyOtpRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auth/login")
    Call<MessageResponse> requestOtp(@Body OtpRequest request);

    @POST("api/auth/verify-otp")
    Call<AuthResponse> verifyOtp(@Body VerifyOtpRequest request);

    @GET("api/user/profile")
    Call<String> getProfile(@Header("Authorization") String token);

    @POST("api/seller/listings")
    Call<String> addProduct(@Header("Authorization") String token,@Body CreateListingRequest createListingRequest);

    @GET("api/public/products")
    Call<List<Product>> getProducts();

}
