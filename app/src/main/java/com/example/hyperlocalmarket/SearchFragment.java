
package com.example.hyperlocalmarket;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalmarket.adapter.ProductAdapter;
import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;
import com.example.hyperlocalmarket.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private EditText sfSearch;
    private ProgressBar pbSearch;
    private RecyclerView rvSearchProducts;

    private List<Product> products;
    private ProductAdapter productAdapter;
    private ApiService apiService;
    private TextView tvResult;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.fragment_search,
                container,
                false
        );

        sfSearch = view.findViewById(R.id.sfSearch);
        rvSearchProducts = view.findViewById(R.id.rvSearchProducts);
        pbSearch= view.findViewById(R.id.pbSearch);
        tvResult=view.findViewById(R.id.tvResult);

        products = new ArrayList<>();

        productAdapter = new ProductAdapter(products);

        rvSearchProducts.setLayoutManager(
                new GridLayoutManager(requireContext(), 2)
        );

        rvSearchProducts.setAdapter(productAdapter);

        apiService = RetrofitClient.getApiService();


        setupSearch();

        Bundle bundle = getArguments();

        if (bundle != null) {

            String prompt = bundle.getString("prompt");

            if (prompt != null && !prompt.trim().isEmpty()) {

                sfSearch.setText(prompt);
                sfSearch.setTextColor(Color.GRAY);

                clickedSearch(prompt);
            }
        }

        return view;
    }

    private void setupSearch() {

        sfSearch.setOnEditorActionListener((textView, actionId, event) -> {
            pbSearch.setVisibility(View.VISIBLE);

            boolean isSearchAction =
                    actionId == EditorInfo.IME_ACTION_SEARCH;

            boolean isEnterKey =
                    event != null &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                            event.getAction() == KeyEvent.ACTION_DOWN;

            if (isSearchAction || isEnterKey) {

                String text = sfSearch.getText()
                        .toString()
                        .trim();

                if (!text.isEmpty()) {
                    clickedSearch(text);
                }
                if (text.isEmpty()){
                    pbSearch.setVisibility(View.GONE);
                    tvResult.setText("Enter any relevant value.");

                }

                hideKeyboard();

                return true;
            }

            return false;
        });
    }

    private void clickedSearch(String prompt) {

        apiService.searchProduct(prompt).enqueue(
                new Callback<List<Product>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<Product>> call,
                            @NonNull Response<List<Product>> response
                    ) {
                        pbSearch.setVisibility(View.GONE);

                        if (response.isSuccessful()
                                && response.body() != null) {


                            products.clear();

                            products.addAll(response.body());

                            productAdapter.notifyDataSetChanged();
                            if (response.body().isEmpty()) {
                                tvResult.setVisibility(View.VISIBLE);
                                tvResult.setText("Not Found");
                            } else {
                                tvResult.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<Product>> call,
                            @NonNull Throwable t
                    ) {
                        pbSearch.setVisibility(View.GONE);
                    }
                }
        );
    }

    private void hideKeyboard() {

        InputMethodManager imm =
                (InputMethodManager) requireContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(
                    sfSearch.getWindowToken(),
                    0
            );
        }
    }
}

