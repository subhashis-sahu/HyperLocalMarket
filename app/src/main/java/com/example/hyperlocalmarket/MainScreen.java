package com.example.hyperlocalmarket;


import android.graphics.Color;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;



import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
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


            resetBottomNavIcons();
            zoomSelectedIcon(id);

            // Your existing navigation
            if (id == R.id.nav_home) {

                openFragment(new HomeFragment());

            } else if (id == R.id.nav_search) {

                openFragment(new SearchFragment());

            } else if (id == R.id.nav_sell) {

                openFragment(new AddItemFragment());

            } else if (id == R.id.nav_chat) {

                openFragment(new ChatFragment());

            } else if (id == R.id.nav_profile) {

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
    private void zoomSelectedIcon(int selectedId) {

        View selectedView =
                bottomNavigationView.findViewById(selectedId);

        if (selectedView != null) {

            selectedView.setScaleX(1.23f);
            selectedView.setScaleY(1.23f);
        }
    }
    private void resetBottomNavIcons() {

        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {

            MenuItem menuItem =
                    bottomNavigationView.getMenu().getItem(i);

            View itemView =
                    bottomNavigationView.findViewById(menuItem.getItemId());

            if (itemView != null) {

                itemView.setScaleX(1.0f);
                itemView.setScaleY(1.0f);
            }
        }
    }
}