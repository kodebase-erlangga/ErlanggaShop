package com.example.erlshop;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinkViewHolder> {
    private List<String> titles;
    private List<String> links;

    public LinksAdapter(List<String> titles, List<String> links) {
        this.titles = titles;
        this.links = links;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_card, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        String title = titles.get(position);
        String link = links.get(position);

        holder.titleTextView.setText(title);  // Set the title
        holder.linkTextView.setText(link);    // Set the URL
        holder.linkTextView.setTextColor(Color.parseColor("#FF000000"));
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, linkTextView;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
        }
    }
}