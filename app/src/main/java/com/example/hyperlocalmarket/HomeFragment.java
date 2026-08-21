package com.example.hyperlocalmarket;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalmarket.adapter.ProductAdapter;
import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;
import com.example.hyperlocalmarket.model.Product;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    // Bottom navigation
    boolean isBottomNavHidden = false;
    boolean isHeaderHidden = false;

    LinearLayout layHeader;

    Spinner spinner;
    Button btnPostItem;

    LinearLayout bikes, realEstate, cars, furniture, cardTrending1;

    ImageView imageView;

    ScrollView mainContent;
    View bottomNav;

    RecyclerView rvProducts;

    List<Product> productsList;
    ProductAdapter productAdapter;

    FusedLocationProviderClient fusedLocationProviderClient;


    public HomeFragment() {
        // Required empty constructor
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull android.view.LayoutInflater inflater,
            @Nullable android.view.ViewGroup container,
            @Nullable Bundle savedInstanceState) {


        // Connect Fragment with your XML
        return inflater.inflate(
                R.layout.fragment_home_fragment,
                container,
                false
        );
    }


    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        rvProducts=view.findViewById(R.id.rvProducts);
        productsList=new ArrayList<>();
        productAdapter=new ProductAdapter(productsList);

        rvProducts.setLayoutManager(new GridLayoutManager(requireContext(),2));
        rvProducts.setAdapter(productAdapter);


        loadContent();




        mainContent = view.findViewById(R.id.mainContent);

        spinner = view.findViewById(R.id.spinnerLocation);

        imageView = view.findViewById(R.id.imgAvatar);

        cars = view.findViewById(R.id.cars);
        bikes = view.findViewById(R.id.bikes);
        furniture = view.findViewById(R.id.furniture);
        realEstate = view.findViewById(R.id.realEstate);

        cardTrending1 = view.findViewById(R.id.cardTrending1);
        btnPostItem =view.findViewById(R.id.btnPostItem);

        bottomNav = requireActivity().findViewById(R.id.bottomNavigationView);









        // --------------------------------------------------
        // CATEGORY CLICK
        // --------------------------------------------------

        cars.setOnClickListener(v ->
                selectItem(cars)
        );

        bikes.setOnClickListener(v ->
                selectItem(bikes)
        );

        realEstate.setOnClickListener(v ->
                selectItem(realEstate)
        );

        furniture.setOnClickListener(v ->
                selectItem(furniture)
        );


        // --------------------------------------------------
        // TRENDING CARD
        // --------------------------------------------------

        cardTrending1.setOnClickListener(v -> {

            Intent intent =
                    new Intent(requireContext(), items_screen.class);

            startActivity(intent);
        });


        // --------------------------------------------------
        // PROFILE
        // --------------------------------------------------

        imageView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(requireContext(), activity_profile.class);

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            );

            startActivity(intent);
        });


        // --------------------------------------------------
        // FUSED LOCATION
        // --------------------------------------------------

        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(
                        requireActivity()
                );


        // --------------------------------------------------
        // SPINNER INITIAL VALUE
        // --------------------------------------------------

        String[] test = {"Loading..."};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        test
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);


        // --------------------------------------------------
        // LOCATION PERMISSION
        // --------------------------------------------------

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    101
            );

        } else {

            getLocation();
        }


        // --------------------------------------------------
        // LOCATION PROVIDER CHECK
        // --------------------------------------------------

        checkLocationEnabled();
    }


    // ======================================================
    // CATEGORY SELECTION
    // ======================================================

    private void selectItem(LinearLayout select) {

        LinearLayout[] categories = {
                cars,
                bikes,
                furniture,
                realEstate
        };

        for (LinearLayout view : categories) {

            view.setBackgroundResource(
                    R.drawable.category_chip_default
            );
        }

        select.setBackgroundResource(
                R.drawable.category_chip_selected
        );
    }


    // ======================================================
    // LOCATION ENABLE CHECK
    // ======================================================

    private void checkLocationEnabled() {

        LocationManager locationManager =
                (LocationManager)
                        requireContext()
                                .getSystemService(
                                        android.content.Context.LOCATION_SERVICE
                                );

        boolean isNetworkEnabled =
                locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                );

        boolean isGpsEnabled =
                locationManager.isProviderEnabled(
                        LocationManager.GPS_PROVIDER
                );


        if (!isGpsEnabled && !isNetworkEnabled) {

            new AlertDialog.Builder(requireContext())
                    .setTitle("Enable Location")
                    .setMessage(
                            "Please turn on location to continue"
                    )
                    .setPositiveButton(
                            "Settings",
                            (dialog, which) -> {

                                Intent intent =
                                        new Intent(
                                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                                        );

                                startActivity(intent);
                            }
                    )
                    .setNegativeButton(
                            "Cancel",
                            null
                    )
                    .show();
        }
    }


    // ======================================================
    // GET LOCATION
    // ======================================================

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    101
            );

            return;
        }


        fusedLocationProviderClient
                .getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location != null) {

                        double lat =
                                location.getLatitude();

                        double lng =
                                location.getLongitude();

                        Log.d(
                                "GPS",
                                lat + " " + lng
                        );

                        setSpinnerLocation(lat, lng);

                    } else {

                        Log.d(
                                "GPS",
                                "Location null"
                        );

                        loadFallbackSpinner();
                    }
                });
    }


    // ======================================================
    // GEOCODER
    // ======================================================

    private void setSpinnerLocation(
            double lat,
            double lng) {

        try {

            Geocoder geocoder =
                    new Geocoder(
                            requireContext(),
                            Locale.getDefault()
                    );


            List<Address> addresses =
                    geocoder.getFromLocation(
                            lat,
                            lng,
                            1
                    );


            String place =
                    "Unknown Location";


            if (addresses != null &&
                    !addresses.isEmpty()) {

                Address address =
                        addresses.get(0);


                if (address.getSubLocality() != null) {

                    place =
                            address.getSubLocality();

                } else if (
                        address.getLocality() != null) {

                    place =
                            address.getLocality();

                } else if (
                        address.getSubAdminArea() != null) {

                    place =
                            address.getSubAdminArea();

                } else if (
                        address.getAdminArea() != null) {

                    place =
                            address.getAdminArea();
                }
            }


            String[] locations = {
                    place
            };


            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(
                            requireContext(),
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


    // ======================================================
    // FALLBACK SPINNER
    // ======================================================

    private void loadFallbackSpinner() {

        String[] locations = {
                "Loading..."
        };


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        locations
                );


        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );


        spinner.setAdapter(adapter);
    }


    // ======================================================
    // PERMISSION RESULT
    // ======================================================

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );


        if (requestCode == 101 &&
                grantResults.length > 0 &&
                grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {

            getLocation();
        }
    }


    // ======================================================
    // ON RESUME
    // ======================================================

    @Override
    public void onResume() {

        super.onResume();

        checkLocationEnabled();
    }

    public void loadContent(){
        ApiService apiService= RetrofitClient.getApiService();
        apiService.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    productsList.clear();
                    productsList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();

                }
                else {
                    Log.e("PRODUCT_API", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("PRODUCT_API", "API Failed: ", t);

            }
        });
    }
}