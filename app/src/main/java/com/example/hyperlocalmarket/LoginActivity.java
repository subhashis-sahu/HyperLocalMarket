package com.example.hyperlocalmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvGoToRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        // If already logged in, skip to Home
//        SharedPreferences prefs = getSharedPreferences("marketplace_prefs", MODE_PRIVATE);
//        if (prefs.getBoolean("is_logged_in", false)) {
//            startActivity(new Intent(this, HomeActivity.class));
//            finish();
//            return;
//        }
//
//        setContentView(R.layout.activity_login);
//
//        etEmail       = findViewById(R.id.etEmail);
//        etPassword    = findViewById(R.id.etPassword);
//        btnLogin      = findViewById(R.id.btnLogin);
//        tvGoToRegister= findViewById(R.id.tvGoToRegister);
//        progressBar   = findViewById(R.id.progressBar);
//
//        btnLogin.setOnClickListener(v -> attemptLogin());
//
//        tvGoToRegister.setOnClickListener(v ->
//            startActivity(new Intent(this, RegisterActivity.class))
//        );
//    }
//
//    private void attemptLogin() {
//        String email    = etEmail.getText().toString().trim();
//        String password = etPassword.getText().toString().trim();
//
//        // Validation
//        if (TextUtils.isEmpty(email)) {
//            etEmail.setError("Email is required");
//            etEmail.requestFocus();
//            return;
//        }
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            etEmail.setError("Enter a valid email");
//            etEmail.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(password)) {
//            etPassword.setError("Password is required");
//            etPassword.requestFocus();
//            return;
//        }
//        if (password.length() < 6) {
//            etPassword.setError("Password must be at least 6 characters");
//            etPassword.requestFocus();
//            return;
//        }
//
//        // Show loading
//        progressBar.setVisibility(View.VISIBLE);
//        btnLogin.setEnabled(false);
//
//        // Mock login (simulate network delay)
//        new android.os.Handler().postDelayed(() -> {
//            progressBar.setVisibility(View.GONE);
//            btnLogin.setEnabled(true);
//
//            if (MockAuthManager.login(email, password)) {
//                // Save login state
//                SharedPreferences.Editor editor = getSharedPreferences("marketplace_prefs", MODE_PRIVATE).edit();
//                editor.putBoolean("is_logged_in", true);
//                editor.putString("user_email", email);
//                editor.putString("user_name", MockAuthManager.getUserName(email));
//                editor.apply();
//
//                Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, HomeActivity.class));
//                finish();
//            } else {
//                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
//            }
//        }, 1200); // Simulate 1.2s network call


        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v->
        {
            Intent intent=new Intent(LoginActivity.this, MainScreen.class);
            startActivity(intent);
        });

    }
}
