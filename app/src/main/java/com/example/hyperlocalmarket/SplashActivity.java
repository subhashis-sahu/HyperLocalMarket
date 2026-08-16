package com.example.hyperlocalmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            new Handler().postDelayed(()-> {
                SharedPreferences preferences=getSharedPreferences("auth",MODE_PRIVATE);
                String jwt=preferences.getString("jwt",null);

                if (jwt==null){
                    Intent intent =
                            new Intent(SplashActivity.this,
                                    RegisterActivity.class);

                    startActivity(intent);
                }
                else{
                    Intent intent =
                            new Intent(SplashActivity.this,
                                    MainScreen.class);

                    startActivity(intent);
                }
                finish();
            },SPLASH_TIME);


            return insets;
        });
    }
}