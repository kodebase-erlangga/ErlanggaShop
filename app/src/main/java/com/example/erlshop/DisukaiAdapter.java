package com.example.erlshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DisukaiAdapter extends RecyclerView.Adapter<DisukaiAdapter.DisukaiViewHolder> {
    private List<String> links;

    public DisukaiAdapter(List<String> links) {
        this.links = links;
    }

    @NonNull
    @Override
    public DisukaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new DisukaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisukaiViewHolder holder, int position) {
        String link = links.get(position);
        holder.linkTextView.setText(link);
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    static class DisukaiViewHolder extends RecyclerView.ViewHolder {
        TextView linkTextView;

        DisukaiViewHolder(@NonNull View itemView) {
            super(itemView);
            linkTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}