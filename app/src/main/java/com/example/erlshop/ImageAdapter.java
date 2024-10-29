package com.example.erlshop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final int[] images;
    private final String[] judulBuku;
    private final Context context;
    private final String CLICK_COUNT_PREF = "click_count_pref"; // SharedPreferences key
    private Dialog imageDialog;
    private final Handler handler = new Handler();
    private boolean isLongPressed = false;

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

        // Handle short click to open DetailActivity
        holder.imageView.setOnClickListener(v -> {
            if (!isLongPressed) { // Only proceed if it's not a long press
                SharedPreferences sharedPreferences = context.getSharedPreferences(CLICK_COUNT_PREF, Context.MODE_PRIVATE);
                int currentCount = sharedPreferences.getInt("cover_" + position, 0); // Get current count
                currentCount++;

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("cover_" + position, currentCount);
                editor.apply();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("imageResId", images[position]);
                intent.putExtra("clickCount", currentCount);
                intent.putExtra("bookTitle", judulBuku[position]); // Pass the book title
                context.startActivity(intent);
            }
        });

        // Handle long press to show larger image in a dialog
        holder.imageView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Start detecting long press after 0.7 seconds
                    isLongPressed = false;
                    handler.postDelayed(() -> {
                        isLongPressed = true;
                        showImagePopup(images[position]); // Show dialog on long press
                    }, 700); // 0.7 seconds
                    return true;

                case MotionEvent.ACTION_UP:
                    // Cancel long press detection if released
                    handler.removeCallbacksAndMessages(null);
                    if (!isLongPressed) {
                        // If it was a short click, perform the click action
                        holder.imageView.performClick(); // This will trigger the OnClickListener
                    } else {
                        // If it was a long press, dismiss the dialog
                        dismissImagePopup();
                    }
                    isLongPressed = false; // Reset long press status
                    return true;

                case MotionEvent.ACTION_CANCEL:
                    // Cancel long press detection if released or canceled
                    handler.removeCallbacksAndMessages(null);
                    if (isLongPressed) {
                        dismissImagePopup();
                        isLongPressed = false; // Reset long press status after releasing
                    }
                    return false;
            }
            return false;
        });
    }

    // Method to show a pop-up dialog with a larger image
    private void showImagePopup(int imageResId) {
        if (imageDialog == null) {
            imageDialog = new Dialog(context);
            imageDialog.setContentView(R.layout.dialog_image_popup);
        }

        ImageView largeImageView = imageDialog.findViewById(R.id.largeImageView);
        largeImageView.setImageResource(imageResId);

        imageDialog.show();
    }

    // Method to dismiss the pop-up dialog
    private void dismissImagePopup() {
        if (imageDialog != null && imageDialog.isShowing()) {
            imageDialog.dismiss();
        }
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
