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
    private List<String> linkList;

    public LinksAdapter(List<String> linkList) {
        this.linkList = linkList;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_card, parent, false);
        return new LinkViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        String link = linkList.get(position);
        holder.textView.setText(link); // Display the URL
        holder.textView.setTextColor(Color.parseColor("#FF000000"));
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.linkTextView);
        }
    }
}