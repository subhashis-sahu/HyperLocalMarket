package com.example.hyperlocalmarket.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalmarket.R;
import com.example.hyperlocalmarket.items_screen;
import com.example.hyperlocalmarket.model.Product;
import com.example.hyperlocalmarket.viewholder.CategoryProductViewHolder;
import com.example.hyperlocalmarket.viewholder.ProductViewHolder;

import java.util.ArrayList;

public class CategoryProductAdaptor extends RecyclerView.Adapter<CategoryProductViewHolder> {

    private ArrayList<Product> products;

    public CategoryProductAdaptor(ArrayList<Product> products){
        this.products=products;
    }
    @NonNull
    @Override
    public CategoryProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new CategoryProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductViewHolder holder, int position) {
        Product product=products.get(position);

        String imageUrl = product.getImageUrl();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            holder.ipProductImage.setImageURI(Uri.parse(imageUrl));
        } else {
            holder.ipProductImage.setImageResource(R.drawable.hide_image_24dp_e3e3e3_fill0_wght400_grad0_opsz24);
        }

        holder.ipProductName.setText(product.getTitle());
        holder.ipProductPrice.setText("₹" + product.getPrice());
        holder.itemView.setOnClickListener(v->{
            Long productId=product.getId();
            Intent intent=new Intent(v.getContext(), items_screen.class);
            intent.putExtra("productId",productId);
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
