package com.example.erlshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            viewPager.setAdapter(new HomeFragmentAdapter(activity));
        }

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(new String[]{"SD", "SMP", "SMA"}[position]);
        }).attach();

        setupUserProfileCard(view);
        setupIconLayouts(view);

        // Set up the options menu for optionsImageView
        ImageView optionsImageView = view.findViewById(R.id.optionsImageView);
        optionsImageView.setOnClickListener(v -> showOptionsMenu(v));

        return view;
    }

    private void setupUserProfileCard(View view) {
        SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", getActivity().MODE_PRIVATE);
        String userName = preferences.getString("userName", "User");
        String userDivision = preferences.getString("userDivision", "Division");
        String profileImageUri = preferences.getString("userProfilePic", "");

        TextView greetingTextView = view.findViewById(R.id.greetingTextView);
        greetingTextView.setText("Halo, " + userName);

        TextView divisionTextView = view.findViewById(R.id.division);
        divisionTextView.setText(userDivision);

        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        if (profileImageUri != null && !profileImageUri.isEmpty()) {
            Glide.with(this).load(Uri.parse(profileImageUri)).into(profileImageView);
        } else {
            profileImageView.setImageResource(R.drawable.baseline_account_circle_24);
        }
    }

    private void setupIconLayouts(View view) {
        LinearLayout internetLayout = view.findViewById(R.id.internetLayout);
        LinearLayout calendarLayout = view.findViewById(R.id.calendarLayout);
        LinearLayout bukuLayout = view.findViewById(R.id.bukulayout);
        LinearLayout perpusLayout = view.findViewById(R.id.perpusLayout);
        LinearLayout iconBaru1Layout = view.findViewById(R.id.iconBaru1Layout);
        LinearLayout iconBaru2Layout = view.findViewById(R.id.iconBaru2Layout);
        LinearLayout iconBaru3Layout = view.findViewById(R.id.iconBaru3Layout);
        LinearLayout iconBaru4Layout = view.findViewById(R.id.iconBaru4Layout);

        internetLayout.setOnClickListener(v -> loadFragment(new AplikasiFragment()));
        calendarLayout.setOnClickListener(v -> loadFragment(new GroupFragment()));
        bukuLayout.setOnClickListener(v -> loadFragment(new MenuFragment()));
        perpusLayout.setOnClickListener(v -> loadFragment(new DisukaiFragment()));
        iconBaru1Layout.setOnClickListener(v -> loadFragment(new CatatanFragment()));
        iconBaru2Layout.setOnClickListener(v -> loadFragment(new InternetFragment()));
        iconBaru3Layout.setOnClickListener(v -> loadFragment(new KoleksiFragment()));
        iconBaru4Layout.setOnClickListener(v -> loadFragment(new PetaFragment()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showOptionsMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    loadFragment(new ProfileFragment()); // Navigate to ProfileFragment
                    return true;
                case R.id.nav_logout:
                    logout(); // Call the logout method
                    return true;
                default:
                    return false; // Handle other menu items if any
            }
        });
        popupMenu.show(); // Show the popup menu
    }

    private void logout() {
        SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(getActivity(), "Logout successful", Toast.LENGTH_SHORT).show();
    }
}
