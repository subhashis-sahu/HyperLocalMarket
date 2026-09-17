package com.example.hyperlocalmarket.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hyperlocalmarket.R;
import com.example.hyperlocalmarket.viewholder.ProductImageVH;

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageVH> {

    private final List<Uri> images;
    public ProductImageAdapter(List<Uri> images) {
        this.images = images;
    }
    @NonNull
    @Override
    public ProductImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(
                    R.layout.item_product_image,
                    parent,
                    false
            );

        return new ProductImageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageVH holder, int position) {
        holder.imageView.setImageURI(images.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
