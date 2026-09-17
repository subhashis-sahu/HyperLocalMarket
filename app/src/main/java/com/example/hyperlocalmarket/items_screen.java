package com.example.hyperlocalmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;
import com.example.hyperlocalmarket.model.ProductInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class items_screen extends AppCompatActivity {

    private TextView itemTitle,itemPrice,
            itemCondition,itemStatus,itemLocation,
            itemDescription,sellerName,sellerAddress,sellerPhNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_items_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        itemTitle = findViewById(R.id.itemTitle);
        itemPrice = findViewById(R.id.itemPrice);
        itemCondition = findViewById(R.id.itemCondition);
        itemStatus = findViewById(R.id.itemStatus);
        itemLocation = findViewById(R.id.itemLocation);
        itemDescription = findViewById(R.id.itemDescription);


        sellerName = findViewById(R.id.sellerName);
        sellerAddress = findViewById(R.id.sellerAddress);
        sellerPhNumber = findViewById(R.id.contactSeller);

        RecyclerView recyclerView =
                findViewById(R.id.productImagesRecycler);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false);

        recyclerView.setLayoutManager(layoutManager);

        ApiService apiService= RetrofitClient.getApiService();
        Long productId=getIntent().getLongExtra("productId",-1);

        apiService.getProduct(productId).enqueue(new Callback<ProductInfo>() {
            @Override
            public void onResponse(Call<ProductInfo> call, Response<ProductInfo> response) {
                if (response.isSuccessful() && response.body()!=null){
                    ProductInfo product=response.body();
                    itemTitle.setText(product.getTitle());
                    itemPrice.setText("₹" + product.getPrice());
                    itemCondition.setText(product.getCondition());
                    itemStatus.setText(product.getStatus());
                    itemLocation.setText(product.getLocation());
                    itemDescription.setText(product.getDescription());

                    sellerName.setText(product.getSeller().getName());

                    Bundle bundle=new Bundle();
                    bundle.putString("sellerNumber",product.getSeller().getPhNumber().toString());
                    sellerPhNumber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(items_screen.this, ConversationActivity.class);


                            intent.putExtra("productId", productId);
                            intent.putExtra("sellerId", product.getSeller().getPhNumber());

                            intent.putExtra("productTitle", product.getTitle());
                            intent.putExtra("productPrice", product.getPrice().toString());
                            intent.putExtra("productImage", product.getImageUrl());

                            intent.putExtra("sellerName", product.getSeller().getName());

                            startActivity(intent);

                        }
                    });
                    sellerAddress.setText(product.getSeller().getAddress());

                }
            }

            @Override
            public void onFailure(Call<ProductInfo> call, Throwable t) {

            }
        });

//        int[] images = {
//                R.drawable.sample_product,
//                R.drawable.sample_product,
//                R.drawable.sample_product
//        };
//
//        ProductImageAdapter adapter =
//                new ProductImageAdapter(images);
//
//        recyclerView.setAdapter(adapter);
    }
}