package com.example.erlshop;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.Window;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import android.graphics.drawable.ColorDrawable;
import android.app.Dialog;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Memeriksa apakah pengguna sudah login
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            // Jika belum login, arahkan ke LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String email = preferences.getString("userEmail", ""); // Ambil email pengguna dari SharedPreferences

        // Initialize views
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Set email di header NavigationView
        View headerView = navigationView.getHeaderView(0);  // Mendapatkan header layout
        emailTextView = headerView.findViewById(R.id.profile_email); // Sesuaikan ID dengan TextView di header_layout
        emailTextView.setText(email);  // Tampilkan email di TextView header

//        // Setup ActionBarDrawerToggle
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        // Set default fragment
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Bottom navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.Galeri) {
                replaceFragment(new GaleriFragment());
                return true;
            } else if (itemId == R.id.Koleksi) {
                replaceFragment(new KoleksiFragment());
                return true;
            } else if (itemId == R.id.Disukai) {
                replaceFragment(new DisukaiFragment());
                return true;
            } else {
                return false;
            }
        });

        // Navigation drawer item selection
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_profile) {
                replaceFragment(new ProfileFragment());
                drawerLayout.closeDrawers();
                return true;
            } else if (itemId == R.id.nav_logout) {
                logout();  // Call the logout method
                drawerLayout.closeDrawers();
                return true;
            } else {
                return false;
            }
        });
//        fab.setOnClickListener(view -> showBottomDialog());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Toast.makeText(MainActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
}