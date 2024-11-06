package com.example.erlshop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.erlshop.databinding.ActivityLoginBinding;
import android.database.Cursor;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Memeriksa apakah pengguna sudah login
        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Jika pengguna sudah login, langsung buka MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return; // Keluar dari onCreate
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Login Button Listener
        binding.loginButton.setOnClickListener(view -> {
            String email = binding.loginEmail.getText().toString();
            String password = binding.loginPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor = databaseHelper.getUserByEmailAndPassword(email, password);

                if (cursor != null && cursor.moveToFirst()) {
                    // Retrieve user details
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String division = cursor.getString(cursor.getColumnIndex("division"));
                    String profileImageUri = cursor.getString(cursor.getColumnIndex("profile_pic"));

                    // Save login status and user details in SharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userEmail", email);
                    editor.putString("userName", name);
                    editor.putString("userDivision", division);
                    editor.putString("userProfilePic", profileImageUri);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Redirect to Signup Activity
        binding.signupRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Toggle Password Visibility
        binding.eyeIcon.setOnClickListener(view -> {
            if (binding.loginPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                binding.loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.eyeIcon.setImageResource(R.drawable.baseline_visibility_24);
            } else {
                binding.loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.eyeIcon.setImageResource(R.drawable.baseline_visibility_off_24);
            }
            binding.loginPassword.setSelection(binding.loginPassword.length());
        });
    }
}
