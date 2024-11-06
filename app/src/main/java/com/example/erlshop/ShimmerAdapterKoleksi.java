package com.example.erlshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class ShimmerAdapterKoleksi extends RecyclerView.Adapter<ShimmerAdapterKoleksi.ShimmerViewHolder> {

    private static final int ITEM_COUNT = 10; // Jumlah item shimmer yang akan ditampilkan

    @Override
    public ShimmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_koleksi_shimmer, parent, false);
        return new ShimmerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShimmerViewHolder holder, int position) {
        // No need to bind data, as it's just a placeholder
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    public static class ShimmerViewHolder extends RecyclerView.ViewHolder {
        public ShimmerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
