package com.example.hyperlocalmarket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalmarket.R;
import com.example.hyperlocalmarket.model.Product;
import com.example.hyperlocalmarket.viewholder.ProductViewHolder;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<Product> products;

    public ProductAdapter(List<Product> products){
        this.products=products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product=products.get(position);
        holder.productTitle.setText(product.getTitle());
        holder.productPrice.setText("₹"+product.getPrice());
        holder.productLocation.setText(product.getLocation());



    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
