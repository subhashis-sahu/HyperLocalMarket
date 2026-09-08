package com.example.hyperlocalmarket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hyperlocalmarket.adapter.ProductAdapter;
import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;
import com.example.hyperlocalmarket.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    private List<LinearLayout> categoryLayouts = new ArrayList<>();
    RecyclerView rvCategoriesWise;

    List<Product> products;

    ProductAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_category, container, false);
        categoryLayouts.add(view.findViewById(R.id.realEstate));
        categoryLayouts.add(view.findViewById(R.id.furniture));
        categoryLayouts.add(view.findViewById(R.id.cars));
        categoryLayouts.add(view.findViewById(R.id.bikes));
        categoryLayouts.add(view.findViewById(R.id.electronics));
        categoryLayouts.add(view.findViewById(R.id.books));
        categoryLayouts.add(view.findViewById(R.id.computers));
        categoryLayouts.add(view.findViewById(R.id.gaming));
        categoryLayouts.add(view.findViewById(R.id.appliances));
        categoryLayouts.add(view.findViewById(R.id.sports));
        categoryLayouts.add(view.findViewById(R.id.others));

        rvCategoriesWise=view.findViewById(R.id.rvCategoriesWise);

        products=new ArrayList<>();

        adapter=new ProductAdapter(products);

        rvCategoriesWise.setLayoutManager(
                new GridLayoutManager(requireContext(), 2)
        );

        rvCategoriesWise.setAdapter(adapter);

        ApiService apiService = RetrofitClient.getApiService();

        getClicked(0, categoryLayouts, apiService);
        getClicked(1, categoryLayouts, apiService);
        getClicked(2, categoryLayouts, apiService);

        return view;

    }

    void getClicked(
            int idx,
            List<LinearLayout> categoriesList,
            ApiService apiService) {

        categoriesList.get(idx).setOnClickListener(v -> {

            apiService.getProductsByCategory((long) idx + 1)
                    .enqueue(new Callback<List<Product>>() {

                        @Override
                        public void onResponse(
                                Call<List<Product>> call,
                                Response<List<Product>> response) {

                            if (response.isSuccessful()
                                    && response.body() != null) {

                                products.clear();
                                products.addAll(response.body());

                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(
                                Call<List<Product>> call,
                                Throwable t) {
                            Toast.makeText(requireContext(), t+"", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}