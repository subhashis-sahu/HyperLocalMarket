package com.example.hyperlocalmarket.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hyperlocalmarket.R;

public class ProductImageVH extends RecyclerView.ViewHolder {

    public ImageView imageView;

    public ProductImageVH(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.ivProductImage);
    }
}
