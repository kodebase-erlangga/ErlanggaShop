package com.example.erlshop;

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
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3)); // 3 images per row
        ImageAdapter adapter = new ImageAdapter(getActivity(), images, judulBuku);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
