package com.example.erlshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi TabLayout dan ViewPager2
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // Inisialisasi adapter untuk ViewPager2
        FragmentActivity activity = getActivity();
        if (activity != null) {
            viewPager.setAdapter(new HomeFragmentAdapter(activity));
        }

        // Hubungkan TabLayout dengan ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("SD");
                    break;
                case 1:
                    tab.setText("SMP");
                    break;
                case 2:
                    tab.setText("SMA");
                    break;
            }
        }).attach();

        // Inisialisasi icon layouts
        setupIconLayouts(view);

        return view;
    }

    // Method untuk mengatur layout icon
    private void setupIconLayouts(View view) {
        // LinearLayout untuk Internet dan Calendar
        LinearLayout internetLayout = view.findViewById(R.id.internetLayout);
        LinearLayout calendarLayout = view.findViewById(R.id.calendarLayout);
        LinearLayout bukuLayout = view.findViewById(R.id.bukulayout);
        LinearLayout perpusLayout = view.findViewById(R.id.perpusLayout);
        LinearLayout iconBaru1Layout = view.findViewById(R.id.iconBaru1Layout);
        LinearLayout iconBaru2Layout = view.findViewById(R.id.iconBaru2Layout);
        LinearLayout iconBaru3Layout = view.findViewById(R.id.iconBaru3Layout);
        LinearLayout iconBaru4Layout = view.findViewById(R.id.iconBaru4Layout);

        // Klik Internet (icon dan keterangan) untuk membuka AplikasiFragment
        internetLayout.setOnClickListener(v -> loadFragment(new AplikasiFragment()));

        // Klik Calendar (icon dan keterangan) untuk membuka GroupFragment
        calendarLayout.setOnClickListener(v -> loadFragment(new GroupFragment()));

        // Tambahkan listener untuk icon lainnya sesuai kebutuhan
        bukuLayout.setOnClickListener(v -> loadFragment(new MenuFragment()));
        perpusLayout.setOnClickListener(v -> loadFragment(new DisukaiFragment()));
        iconBaru1Layout.setOnClickListener(v -> loadFragment(new CatatanFragment()));
        iconBaru2Layout.setOnClickListener(v -> loadFragment(new InternetFragment()));
        iconBaru3Layout.setOnClickListener(v -> loadFragment(new KoleksiFragment()));
        iconBaru4Layout.setOnClickListener(v -> loadFragment(new PetaFragment()));
    }

    // Method untuk mengganti fragment yang ditampilkan
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
