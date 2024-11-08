package com.example.erlshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private Context context;
    private List<BannerItem> bannerItems;
    private ImageLoader imageLoader;

    public BannerAdapter(Context context, List<BannerItem> bannerItems) {
        this.context = context;
        this.bannerItems = bannerItems;
        this.imageLoader = new ImageLoader(Volley.newRequestQueue(context), new BitmapCache());
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider_banner, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        BannerItem bannerItem = bannerItems.get(position);

        // Correct the method to getBannerCover
        holder.imageView.setImageUrl(bannerItem.getBannerCover(), imageLoader);

        // Set click listener to open URL in WebViewActivity (if applicable)
        holder.itemView.setOnClickListener(v -> {
            String linkUrl = bannerItem.getLinkUrl(); // Mendapatkan URL yang terkait dengan banner
            if (linkUrl != null && !linkUrl.isEmpty()) { // Memastikan URL tidak null atau kosong
                Intent intent = new Intent(context, WebViewActivity.class); // Membuat intent untuk berpindah ke WebViewActivity
                intent.putExtra("URL", linkUrl); // Mengirim URL ke WebViewActivity
                context.startActivity(intent); // Memulai aktivitas
            }
        });
    }


    @Override
    public int getItemCount() {
        return bannerItems.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView imageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSlider);
        }
    }
}