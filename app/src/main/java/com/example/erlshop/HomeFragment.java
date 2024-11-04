package com.example.erlshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import androidx.drawerlayout.widget.DrawerLayout;
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
    private DrawerLayout drawerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize DrawerLayout
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        // Initialize TabLayout and ViewPager2
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            viewPager.setAdapter(new HomeFragmentAdapter(activity));
        }

        // Connect TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(new String[]{"SD", "SMP", "SMA"}[position]);
        }).attach();

        setupUserProfileCard(view);  // Set up the user profile card
        setupIconLayouts(view);  // Initialize icon layouts

        return view;
    }

    private void setupUserProfileCard(View view) {
        SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", getActivity().MODE_PRIVATE);
        String userId = preferences.getString("userId", "");

        // Inflate and set up the user profile card view
        View userProfileCard = LayoutInflater.from(getActivity()).inflate(R.layout.user_profile_card, null);
        ImageView profileImageView = userProfileCard.findViewById(R.id.profileImageView);
        TextView greetingTextView = userProfileCard.findViewById(R.id.greetingTextView);
        ImageView optionsImageView = userProfileCard.findViewById(R.id.optionsImageView);

        // Load user profile information
        loadUserProfile(userId, profileImageView, greetingTextView);

        // Show options menu on optionsImageView click
//        optionsImageView.setOnClickListener(v -> showOptionsMenu(optionsImageView));

        // Add the card view to the fragment layout
        LinearLayout profileContainer = view.findViewById(R.id.profileContainer);
        profileContainer.addView(userProfileCard);
    }

    private void loadUserProfile(String userId, ImageView profileImageView, TextView greetingTextView) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        // Query the database for user information
        Cursor cursor = databaseHelper.getUserById(userId);
        if (cursor != null && cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndex("name"));
            String profileImageUri = cursor.getString(cursor.getColumnIndex("profile_pic")); // Use the correct column name

            // Update UI
            greetingTextView.setText("Hallo, " + userName);
            if (profileImageUri != null && !profileImageUri.isEmpty()) {
                Glide.with(this).load(Uri.parse(profileImageUri)).into(profileImageView);
            } else {
                profileImageView.setImageResource(R.drawable.baseline_account_circle_24); // Default image if no URI
            }
            cursor.close(); // Close the cursor after use
        } else {
            // Handle case where user is not found or cursor is null
            greetingTextView.setText("Hallo, User"); // Default greeting
            profileImageView.setImageResource(R.drawable.baseline_account_circle_24); // Default image
        }
    }

//    private void showOptionsMenu(View view) {
//        PopupMenu popupMenu = new PopupMenu(getContext(), view);
//        popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_profile:
//                    loadFragment(new ProfileFragment());
//                    drawerLayout.closeDrawers();
//                    return true;
//                case R.id.nav_logout:
//                    logout();
//                    drawerLayout.closeDrawers();
//                    return true;
//                default:
//                    return false;
//            }
//        });
//        popupMenu.show();
//    }

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

    private void logout() {
        SharedPreferences preferences = getActivity().getSharedPreferences("loginPrefs", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
        Toast.makeText(getActivity(), "Logout successful", Toast.LENGTH_SHORT).show();
    }
}
