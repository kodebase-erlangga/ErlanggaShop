package com.example.erlshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BannerSliderAdapter extends RecyclerView.Adapter<BannerSliderAdapter.SliderViewHolder> {

    private Context context; // Context of the application
    private List<BannerItem> bannerItems; // List of banner items to be displayed

    // Constructor to initialize the adapter
    public BannerSliderAdapter(Context context, List<BannerItem> bannerItems) {
        this.context = context;
        this.bannerItems = bannerItems; // Initialize banner items
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each banner item
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider_banner, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        // Get the banner item at the current position
        BannerItem bannerItem = bannerItems.get(position);
        // Load the image into the ImageView using Picasso
        Picasso.get().load(bannerItem.getBannerCover()).into(holder.imageView);

        // Set click listener to open URL in WebViewActivity
        holder.itemView.setOnClickListener(v -> {
            String linkUrl = bannerItem.getLinkUrl(); // Get the link URL from the banner item
            if (linkUrl != null && !linkUrl.isEmpty()) { // Check if the URL is not null or empty
                Intent intent = new Intent(context, WebViewActivity.class); // Create intent for WebViewActivity
                intent.putExtra("URL", linkUrl); // Pass the URL to the activity
                context.startActivity(intent); // Start the WebViewActivity
            }
        });
    }

    @Override
    public int getItemCount() {
        return bannerItems.size(); // Return the total number of banner items
    }

    // ViewHolder class for each banner item
    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // ImageView to display banner image

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSlider); // Initialize the ImageView
        }
    }
}
