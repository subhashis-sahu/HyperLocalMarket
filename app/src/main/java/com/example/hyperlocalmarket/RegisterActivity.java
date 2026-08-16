package com.example.hyperlocalmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;
import com.example.hyperlocalmarket.model.AuthResponse;
import com.example.hyperlocalmarket.model.MessageResponse;
import com.example.hyperlocalmarket.model.OtpRequest;
import com.example.hyperlocalmarket.model.VerifyOtpRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etPhoneNumber;
    private EditText etOtp;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etOtp = findViewById(R.id.etOtp);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String number =
                        etPhoneNumber.getText()
                                .toString()
                                .trim();

                if (etOtp.getVisibility() == View.GONE) {

                    if (number.length() != 10) {

                        Toast.makeText(
                                RegisterActivity.this,
                                "Invalid Phone Number",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    Long phNumber =
                            Long.parseLong(number);

                    OtpRequest otpRequest =
                            new OtpRequest(phNumber);

                    ApiService apiService =
                            RetrofitClient.getApiService();

                    apiService.requestOtp(otpRequest)
                            .enqueue(new Callback<MessageResponse>() {

                                @Override
                                public void onResponse(
                                        Call<MessageResponse> call,
                                        Response<MessageResponse> response) {

                                    if (response.isSuccessful()
                                            && response.body() != null) {

                                        Toast.makeText(
                                                RegisterActivity.this,
                                                response.body().getMessage(),
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        // Show OTP field
                                        etOtp.setVisibility(View.VISIBLE);

                                        // Change button text
                                        btnLogin.setText("Verify OTP");

                                        // Don't allow phone number
                                        // to be changed now
                                        etPhoneNumber.setEnabled(false);

                                    }else {
                                        try {
                                            String error = response.errorBody() != null
                                                    ? response.errorBody().string()
                                                    : "No error body";

                                            Toast.makeText(
                                                    RegisterActivity.this,
                                                    "HTTP " + response.code() + "\n" + error,
                                                    Toast.LENGTH_LONG
                                            ).show();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(
                                        Call<MessageResponse> call,
                                        Throwable t) {

                                    Toast.makeText(
                                            RegisterActivity.this,
                                            "Error: " + t.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            });

                }

                // =========================
                // VERIFY OTP
                // =========================

                else {

                    String otpText =
                            etOtp.getText()
                                    .toString()
                                    .trim();

                    if (otpText.length() != 6) {

                        Toast.makeText(
                                RegisterActivity.this,
                                "Enter a valid OTP",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    int otp =
                            Integer.parseInt(otpText);

                    Long phNumber =
                            Long.parseLong(number);

                    VerifyOtpRequest otpRequest =
                            new VerifyOtpRequest(
                                    phNumber,
                                    otp
                            );

                    ApiService apiService =
                            RetrofitClient.getApiService();

                    apiService.verifyOtp(otpRequest)
                            .enqueue(new Callback<AuthResponse>() {

                                @Override
                                public void onResponse(
                                        Call<AuthResponse> call,
                                        Response<AuthResponse> response) {

                                    if (response.isSuccessful()
                                            && response.body() != null) {

                                        String jwt =
                                                response.body().getToken();

                                        // =========================
                                        // SAVE JWT
                                        // =========================

                                        SharedPreferences preferences =
                                                getSharedPreferences(
                                                        "auth",
                                                        MODE_PRIVATE
                                                );

                                        preferences.edit()
                                                .putString(
                                                        "jwt",
                                                        jwt
                                                )
                                                .apply();

                                        Toast.makeText(
                                                RegisterActivity.this,
                                                "Login Successful",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        // =========================
                                        // GO TO MAIN SCREEN
                                        // =========================

                                        Intent intent =
                                                new Intent(
                                                        RegisterActivity.this,
                                                        MainScreen.class
                                                );

                                        startActivity(intent);

                                        finish();

                                    } else {

                                        Toast.makeText(
                                                RegisterActivity.this,
                                                "Invalid OTP",
                                                Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                }

                                @Override
                                public void onFailure(
                                        Call<AuthResponse> call,
                                        Throwable t) {

                                    Toast.makeText(
                                            RegisterActivity.this,
                                            "Error: " + t.getMessage(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            });
                }
            }
        });
    }
}