package com.example.erlshop;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Tab2Fragment extends Fragment {
    private final int[] images = {
            R.drawable.smp_indo, R.drawable.smp_informatika, R.drawable.smp_pai
    };

    private final String[] judulBuku = {
            "Bahasa Indonesia Kelas 7", "Informatika Kelas 8",
            "Pendidikan Agama Islam Kelas 9"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Mendapatkan orientasi layar
        int orientation = getResources().getConfiguration().orientation;
        int columnCount;

        // Jika perangkat adalah tablet, atur kolom berdasarkan orientasi
        if (getResources().getBoolean(R.bool.isTablet)) {
            columnCount = (orientation == Configuration.ORIENTATION_PORTRAIT) ? 4 : 5;
        } else {
            // Untuk smartphone, default-nya 3 kolom
            columnCount = 3;
        }


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        recyclerView.setAdapter(new ImageAdapter(getActivity(), images, judulBuku));

        return view;
    }
}
