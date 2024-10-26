package com.example.erlshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tab1Fragment extends Fragment {
    private final int[] images = {
            R.drawable.cover1,
            R.drawable.cover2,
            R.drawable.cover3,
            R.drawable.cover4,
            R.drawable.cover5,
            R.drawable.cover6,
            R.drawable.cover7,
            R.drawable.cover8,
            R.drawable.cover9,
            R.drawable.cover10,
            R.drawable.cover11,
            R.drawable.cover12,
            R.drawable.cover13,
            R.drawable.cover14
    };

    private final String[] judulBuku = {
            "Manajemen Risiko Bisnis",
            "50 Teknik Praktis Manajemen Perusahaan",
            "Analisis Sumber Daya Manusia dan Statistik",
            "Analisis Kapasitas, Persediaan dan Pemasok",
            "Biologi Molekuler",
            "Manajemen Obesitas",
            "Asuhan Keperawatan Keluarga Dengan Pendekatan Studi Kasus",
            "Bioteknologi Kesehatan", "Home Sweet Loan", "Beruang Pelamu Mau Pup!",
            "Diga Si Naga Yang Apa Adanya", "Parable", "Power Ultimate Service Culture", "Manga"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3)); // 3 columns
        recyclerView.setAdapter(new ImageAdapter(getActivity(), images, judulBuku));

        return view;
    }
}