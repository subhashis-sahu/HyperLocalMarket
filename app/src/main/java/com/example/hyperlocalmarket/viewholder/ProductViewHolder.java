package com.example.hyperlocalmarket.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalmarket.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public ImageView productImage;
    public TextView productTitle;
    public TextView productPrice;
    public TextView productLocation;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.imgProduct);
        productTitle = itemView.findViewById(R.id.tvProductTitle);
        productPrice = itemView.findViewById(R.id.tvProductPrice);
        productLocation = itemView.findViewById(R.id.tvProductLocation);
    }


}
