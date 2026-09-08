package com.example.hyperlocalmarket.api;

import com.example.hyperlocalmarket.model.AuthResponse;
import com.example.hyperlocalmarket.model.Category;
import com.example.hyperlocalmarket.model.CategoryAttribute;
import com.example.hyperlocalmarket.model.CreateListingRequest;
import com.example.hyperlocalmarket.model.MessageResponse;
import com.example.hyperlocalmarket.model.OtpRequest;

import com.example.hyperlocalmarket.model.Product;
import com.example.hyperlocalmarket.model.Profile;
import com.example.hyperlocalmarket.model.VerifyOtpRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/auth/login")
    Call<MessageResponse> requestOtp(@Body OtpRequest request);

    @POST("api/auth/verify-otp")
    Call<AuthResponse> verifyOtp(@Body VerifyOtpRequest request);

    @GET("api/user/profile")
    Call<Profile> getProfile(@Header("Authorization") String token);

    @POST("api/seller/listings")
    Call<String> addProduct(@Header("Authorization") String token,@Body CreateListingRequest createListingRequest);

    @GET("api/public/products")
    Call<List<Product>> getProducts();

    @GET("api/public/products/byCategories/{id}")
    Call<List<Product>> getProductsByCategory(@Path("id") Long id);

    @GET("api/seller/categories")
    Call<List<Category>> getCategory(@Header("Authorization") String token);

    @GET("api/seller/{categoryId}/attributes")
    Call<List<CategoryAttribute>> getCategoryAttributes(
            @Path("categoryId") Long categoryId,
            @Header("Authorization") String token
    );
    @POST("api/seller/listings")
    Call<Void> createListing(
            @Header("Authorization") String token,
            @Body CreateListingRequest request
    );

    @GET("api/public/products/search")
    Call<List<Product>> searchProduct(@Query("prompt") String promt);
}
