package com.example.erlshop;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinkViewHolder> {
    private List<String> titles;
    private List<String> links;
    private List<String> bannerUrls;

    public LinksAdapter(List<String> titles, List<String> links, List<String> bannerUrls) {
        this.titles = titles != null ? titles : List.of();
        this.links = links != null ? links : List.of();
        this.bannerUrls = bannerUrls != null ? bannerUrls : List.of();
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_card, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        if (position < titles.size() && position < links.size() && position < bannerUrls.size()) {
            String title = titles.get(position);
            String link = links.get(position);
            String bannerUrl = bannerUrls.get(position);

            holder.titleTextView.setText(title);
            holder.linkTextView.setText(link);
            holder.linkTextView.setTextColor(Color.parseColor("#FF000000"));

            // Load the banner image using Glide
            Glide.with(holder.imageBanner.getContext())
                    .load(bannerUrl.isEmpty() ? R.drawable.baseline_account_box_24 : bannerUrl)
                    .placeholder(R.drawable.baseline_account_box_24)
                    .into(holder.imageBanner);
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(Math.min(titles.size(), links.size()), bannerUrls.size());
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBanner;
        TextView titleTextView, linkTextView;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBanner = itemView.findViewById(R.id.imageBanner);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
        }
    }

    // Update data in adapter
    public void updateData(List<String> newTitles, List<String> newLinks, List<String> newBannerUrls) {
        this.titles = newTitles != null ? newTitles : List.of();
        this.links = newLinks != null ? newLinks : List.of();
        this.bannerUrls = newBannerUrls != null ? newBannerUrls : List.of();
        notifyDataSetChanged();
    }
}
