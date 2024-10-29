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

        // Format sesuai kebutuhan
        String formattedText = getFormattedText(position) + " " + link;
        holder.linkTextView.setText(formattedText);
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

    // Method untuk mendapatkan teks format berdasarkan posisi
    private String getFormattedText(int position) {
        switch (position) {
            case 0:
                return "Banner Erlangga Pedia :";
            case 1:
                return "Banner Erklika :";
            case 2:
                return "Banner Kelasku :";
            case 3:
                return "Banner Erlangga Exam :";
            case 4:
                return "Banner Erlangga Online :";
            case 5:
                return "Banner Siswa Erlangga Exam :";
            case 6:
                return "Banner Erlangga Exam :";
            case 7:
                return "Banner Shop Buku Erlangga :";
            default:
                return ""; // Jika lebih dari 8, return kosong
        }
    }
}
