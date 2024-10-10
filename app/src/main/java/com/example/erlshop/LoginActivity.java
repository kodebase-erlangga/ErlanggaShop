package com.example.erlshop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.erlshop.databinding.ActivityLoginBinding;
import android.text.InputType;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                if (checkCredentials) {
                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();  // Close LoginActivity after successful login
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
                binding.eyeIcon.setImageResource(R.drawable.baseline_visibility_24); // Change to visible eye icon
            } else {
                binding.loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.eyeIcon.setImageResource(R.drawable.baseline_visibility_off_24); // Change to hidden eye icon
            }
            binding.loginPassword.setSelection(binding.loginPassword.length()); // Move cursor to the end
        });
    }
}
