package com.example.hyperlocalmarket;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hyperlocalmarket.api.ApiService;
import com.example.hyperlocalmarket.api.RetrofitClient;
import com.example.hyperlocalmarket.model.ProductInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {

    TextView productTitle,productPrice,sellerName,sellerStatus;
    ImageView productImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversation);

        productTitle=findViewById(R.id.mPTitle);
        productPrice = findViewById(R.id.mPPrice);
        productImage = findViewById(R.id.mPImage);
        sellerName = findViewById(R.id.msName);
        sellerStatus = findViewById(R.id.msStatus);

        String productTitle =
                getIntent().getStringExtra("productTitle");

        String productPrice =
                getIntent().getStringExtra("productPrice");

        String productImage =
                getIntent().getStringExtra("productImage");

        String sellerName =
                getIntent().getStringExtra("sellerName");


        this.productTitle.setText(productTitle);
        this.productPrice.setText("₹" + productPrice);
        this.sellerName.setText(
                sellerName == null ? "VZ_User" : sellerName
        );






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}