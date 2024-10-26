package com.example.erlshop;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cek status login
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);  // Ambil status login

        if (!isLoggedIn) {
            // Jika belum login, arahkan ke LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();  // Tutup MainActivity agar tidak bisa diakses tanpa login
            return; // Pastikan tidak melanjutkan eksekusi kode di bawah
        }

        // Initialize views
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Use Toolbar from androidx
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set default fragment when activity is first launched
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
            } else if (itemId == R.id.Profile) {
                replaceFragment(new ProfileFragment());
                return true;
            } else {
                return false;
            }
        });

        // Navigation drawer item selection
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                drawerLayout.closeDrawers();
                return true;
            } else if (itemId == R.id.nav_settings) {
                replaceFragment(new SettingsFragment());
                drawerLayout.closeDrawers();
                return true;
            } else if (itemId == R.id.nav_about) {
                replaceFragment(new AboutUsFragment());
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

        // FloatingActionButton to display a dialog
        fab.setOnClickListener(view -> showBottomDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Function to log out and go to the login activity
    private void logout() {
        Log.d("MainActivity", "Logout menu item clicked");
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();  // Hapus semua data di SharedPreferences
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
