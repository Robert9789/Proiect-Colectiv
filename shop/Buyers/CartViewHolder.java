package com.example.shop.Buyers;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public BreakIterator txtProductPrice;
    public BreakIterator txtProductName;
    public BreakIterator txtProductQuantity;
    public View itemView;

    public CartViewHolder(View view) {
        super(view);


    }
}
