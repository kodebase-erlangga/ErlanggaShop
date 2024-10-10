package com.example.erlshop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Switch switchNotifications, switchDarkMode, switchLocation;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize switches
        switchNotifications = view.findViewById(R.id.switch_notifications);
        switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        switchLocation = view.findViewById(R.id.switch_location);

        // Initialize SharedPreferences
        preferences = getActivity().getSharedPreferences("settingsPrefs", getContext().MODE_PRIVATE);
        editor = preferences.edit();

        // Set switch states from SharedPreferences
        switchNotifications.setChecked(preferences.getBoolean("notifications", false));
        switchDarkMode.setChecked(preferences.getBoolean("dark_mode", false));
        switchLocation.setChecked(preferences.getBoolean("location", false));

        // Set listeners for switches
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("notifications", isChecked);
            editor.apply();
            Toast.makeText(getContext(), "Notifications " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();
            Toast.makeText(getContext(), "Dark Mode " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });

        switchLocation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("location", isChecked);
            editor.apply();
            Toast.makeText(getContext(), "Location " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
