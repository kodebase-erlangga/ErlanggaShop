// BannerSliderAdapter.java
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

    private List<BannerItem> bannerItems;
    private Context context;

    public BannerSliderAdapter(Context context, List<BannerItem> bannerItems) {
        this.context = context;
        this.bannerItems = bannerItems;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider_banner, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        BannerItem bannerItem = bannerItems.get(position);
        Picasso.get().load(bannerItem.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            String linkUrl = bannerItem.getLinkUrl();
            if (linkUrl != null && !linkUrl.isEmpty()) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("URL", linkUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bannerItems.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSlider);
        }
    }
}
