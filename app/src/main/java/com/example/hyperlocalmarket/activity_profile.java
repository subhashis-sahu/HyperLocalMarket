package com.example.hyperlocalmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_profile extends AppCompatActivity {
    ImageView btnBack;



    TextView tvUserPhone,btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBack=findViewById(R.id.btnBack);

        tvUserPhone=findViewById(R.id.tvUserPhone);

        btnLogout=findViewById(R.id.btnLogout);


        SharedPreferences preferences=getSharedPreferences("auth",MODE_PRIVATE);
        String jwt=preferences.getString("jwt",null);

        ApiService apiService= RetrofitClient.getApiService();

        apiService.getProfile("Bearer "+jwt).enqueue(
                new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response!=null){
                            String phNumber=response.body();
                            tvUserPhone.setText(phNumber);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("PROFILE_API", "Error: " + t.getMessage());

                    }
                }
        );
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.edit().clear().apply();
                Intent intent=new Intent(activity_profile.this,RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_profile.this, MainScreen.class);
                startActivity(intent);
            }
        });



    }
}