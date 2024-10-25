package com.example.erlshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<BannerItem> bannerItems;
    private Context context;
    private ImageLoader imageLoader;

    public SliderAdapter(Context context, List<BannerItem> bannerItems) {
        this.context = context;
        this.bannerItems = bannerItems;
        this.imageLoader = new ImageLoader(Volley.newRequestQueue(context), new BitmapCache());
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
        holder.imageView.setImageUrl(bannerItem.getImageUrl(), imageLoader);
    }

    @Override
    public int getItemCount() {
        return bannerItems.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSlider);
        }
    }
}
