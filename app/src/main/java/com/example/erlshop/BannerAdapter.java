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
    private List<String> imageUrls;
    private ImageLoader imageLoader;

    public BannerAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
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
        String imageUrl = imageUrls.get(position);
        holder.imageView.setImageUrl(imageUrl, imageLoader); // Menampilkan gambar dari URL

        // Menambahkan klik listener untuk setiap item banner
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                // Mengambil posisi dinamis menggunakan getAdapterPosition()
                int currentPosition = holder.getAdapterPosition();

                // Membuka aktivitas WebView yang sesuai berdasarkan posisi banner
                switch (currentPosition) {
                    case 0:
                        intent = new Intent(context, WebViewActivity1.class);
                        break;
                    case 1:
                        intent = new Intent(context, WebViewActivity2.class);
                        break;
                    case 2:
                        intent = new Intent(context, WebViewActivity3.class);
                        break;
                    case 3:
                        intent = new Intent(context, WebViewActivity4.class);
                        break;
                    case 4:
                        intent = new Intent(context, WebViewActivity5.class);
                        break;
                    case 5:
                        intent = new Intent(context, WebViewActivity6.class);
                        break;
                    case 6:
                        intent = new Intent(context, WebViewActivity7.class);
                        break;
                    case 7:
                        intent = new Intent(context, WebViewActivity8.class);
                        break;
                    default:
                        break;
                }

                if (intent != null) {
                    context.startActivity(intent);  // Memulai aktivitas yang sesuai
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView imageView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewBanner); // ID dari layout item
        }
    }
}
