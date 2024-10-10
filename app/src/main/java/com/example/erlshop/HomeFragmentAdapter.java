package com.example.erlshop;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HomeFragmentAdapter extends FragmentStateAdapter {

    public HomeFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Tab1Fragment(); // Tab 1
            case 1:
                return new Tab2Fragment(); // Tab 2
            case 2:
                return new Tab3Fragment(); // Tab 3
            default:
                return new Tab1Fragment(); // Default tab yang akan muncul pertama
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Jumlah tab
    }
}