package com.example.erlshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final int[] images;
    private final String[] judulBuku;
    private final Context context;

    // SharedPreferences key
    private final String CLICK_COUNT_PREF = "click_count_pref";

    public ImageAdapter(Context context, int[] images, String[] judulBuku) {
        this.context = context;
        this.images = images;
        this.judulBuku = judulBuku;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);

        // Set onClickListener for each image
        holder.imageView.setOnClickListener(v -> {
            // Update click count using SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences(CLICK_COUNT_PREF, Context.MODE_PRIVATE);
            int currentCount = sharedPreferences.getInt("cover_" + position, 0); // Get current count
            currentCount++; // Increment click count

            // Save updated count back to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("cover_" + position, currentCount);
            editor.apply();

            // Open DetailActivity and pass image, click count, and book title
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("imageResId", images[position]);
            intent.putExtra("clickCount", currentCount);
            intent.putExtra("bookTitle", judulBuku[position]); // Pass the book title
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}