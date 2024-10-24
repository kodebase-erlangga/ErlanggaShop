package com.example.erlshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    private List<BannerItem> bannerItems;  // List berisi objek BannerItem yang berisi gambar dan URL

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

        // Set image URL
        holder.imageView.setImageUrl(bannerItem.getImageUrl(), imageLoader); // Menampilkan gambar dari URL

        // Set onClickListener untuk membuka URL yang bersesuaian
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkUrl = bannerItem.getLinkUrl();
                if (linkUrl != null && !linkUrl.isEmpty()) {
                    // Membuka URL menggunakan Intent
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
                    context.startActivity(browserIntent);
                }
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
            imageView = itemView.findViewById(R.id.imageViewBanner); // ID dari layout item
        }
    }
}
