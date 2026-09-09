package com.example.hyperlocalmarket;


import android.graphics.Color;
import android.os.Bundle;

import android.view.Window;



import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainScreen extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        bottomNavigationView =
                findViewById(R.id.bottomNavigationView);

        // Default screen
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.fragmentContainer,
                            new HomeFragment()
                    )
                    .commit();
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                openFragment(new HomeFragment());

            }
            else if (id == R.id.nav_search){
                openFragment(new SearchFragment());

            }

            else if (id == R.id.nav_sell) {

                openFragment(new AddItemFragment());

            } else if (id == R.id.nav_chat) {

                openFragment(new ChatFragment());
            }
            else if (id == R.id.nav_profile) {

                openFragment(new ProfileFragment());
            }

            return true;
        });
    }

    private void openFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}