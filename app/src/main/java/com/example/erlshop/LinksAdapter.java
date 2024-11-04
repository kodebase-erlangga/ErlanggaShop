package com.example.erlshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinkViewHolder> {
    private final List<String> links;

    public LinksAdapter(List<String> links) {
        this.links = links;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        String link = links.get(position);
        holder.linkTextView.setText(link);
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    static class LinkViewHolder extends RecyclerView.ViewHolder {
        TextView linkTextView;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
        }
    }
}
