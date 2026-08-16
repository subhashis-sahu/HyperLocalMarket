package com.example.hyperlocalmarket;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import android.Manifest;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class MainScreen extends AppCompatActivity {

    Spinner spinner;
    LinearLayout bikes,realEstate,cars,furniture,cardTrending1;
    ImageView imageView;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        spinner = findViewById(R.id.spinnerLocation);
        imageView=findViewById(R.id.imgAvatar);


        cars=findViewById(R.id.cars);
        bikes=findViewById(R.id.bikes);
        furniture=findViewById(R.id.furniture);
        realEstate=findViewById(R.id.realEstate);

        cars.setOnClickListener(v->selectItem(cars));
        bikes.setOnClickListener(v->selectItem(bikes));
        realEstate.setOnClickListener(v->selectItem(realEstate));
        furniture.setOnClickListener(v->selectItem(furniture));

        SharedPreferences sharedPreferences=getSharedPreferences("jwt",MODE_PRIVATE);
        String jwt=sharedPreferences.getString("jwt",null);

        cardTrending1=findViewById(R.id.cardTrending1);
        cardTrending1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainScreen.this,items_screen.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(v->
                {
                    Intent intent = new Intent(MainScreen.this,activity_profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                });

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
        }
        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);

        // TEST SPINNER FIRST
        String[] test = {"Loading..."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                test
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);

        getLocation();

        LocationManager locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        boolean isDataEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGpsEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsEnabled && !isDataEnabled) {
            new AlertDialog.Builder(this)
                    .setTitle("Enable Location")
                    .setMessage("Please turn on location to continue")
                    .setPositiveButton("Settings", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        }
    }

    private void selectItem(LinearLayout select) {
        LinearLayout[] d= {cars,bikes,furniture,realEstate};
        for(LinearLayout v:d)
        {
            v.setBackgroundResource(R.drawable.category_chip_default);
        }
        select.setBackgroundResource(R.drawable.category_chip_selected);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocationManager locationManager =
                (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean isGpsEnabled =
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        boolean isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsEnabled && !isNetworkEnabled) {
            new AlertDialog.Builder(this)
                    .setTitle("Enable Location")
                    .setMessage("Please turn on location to continue")
                    .setPositiveButton("Settings", (dialog, which) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101
            );
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();

                        Log.d("GPS", lat + " " + lng);

                        setSpinnerLocation(lat, lng);
                    } else {
                        Log.d("GPS", "Location null");
                        loadFallbackSpinner();
                    }
                });
    }

    private void setSpinnerLocation(double lat, double lng) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            String place = "Unknown Location";

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                if (address.getSubLocality() != null) {
                    place = address.getSubLocality();     // village / area
                } else if (address.getLocality() != null) {
                    place = address.getLocality();        // city
                } else if (address.getSubAdminArea() != null) {
                    place = address.getSubAdminArea();    // district
                } else if (address.getAdminArea() != null) {
                    place = address.getAdminArea();       // state
                }
            }

            String[] locations = {place};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    locations
            );

            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
            );

            spinner.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFallbackSpinner() {
        String[] locations = {"Loading", "EYahLoading"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                locations
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == 101
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }
}