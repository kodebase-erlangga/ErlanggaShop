package com.example.erlshop;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater; // Impor untuk LayoutInflater
import android.view.View; // Impor untuk View
import android.view.ViewGroup; // Impor untuk ViewGroup

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout; // Impor untuk TabLayout
import com.google.android.material.tabs.TabLayoutMediator; // Impor untuk TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2; // Impor untuk ViewPager2


public class Tab3Fragment extends Fragment {
    private final int[] images = {
            R.drawable.sma_fisik, R.drawable.sma_geog, R.drawable.sma_indo,
            R.drawable.sma_inggris, R.drawable.sma_mtk, R.drawable.sma_pai,
    };

    private final String[] judulBuku = {
            "Fisika kelas 10", "Geografi kelas 10",
            "Bahasa Indonesia kelas 11", "Bahasa Ingris kelas 11",
            "Matematika kelas 12", "Pendidikan Agama Islam kelas 11"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
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
