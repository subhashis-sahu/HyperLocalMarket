package com.example.hyperlocalmarket.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalmarket.R;

public class CategoryProductViewHolder extends RecyclerView.ViewHolder {
    public ImageView ipProductImage;
    public TextView ipProductName,ipProductPrice;

    public CategoryProductViewHolder(@NonNull View itemView) {
        super(itemView);
        ipProductImage=itemView.findViewById(R.id.ipProductImage);
        ipProductName=itemView.findViewById(R.id.ipProductName);
        ipProductPrice=itemView.findViewById(R.id.ipProductPrice);

    }
}
