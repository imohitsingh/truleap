package com.example.eventadminapp1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button         btnLogin;
    private FirebaseAuth   mAuth;
    private ProgressDialog progressDialog;

    // Admin's email and password (for demo purposes; in a real app, implement proper admin management)
    private final String adminEmail = "admin@example.com";
    private final String adminPassword = "password123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if the user is already logged in
        if (mAuth.getCurrentUser() != null) {
            // User is already logged in, navigate directly to MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return; // Exit onCreate to avoid executing further code
        }


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        btnLogin.setOnClickListener(v -> loginAdmin());
    }

    private void loginAdmin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email required");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        // For demo, we check if the email and password match the admin credentials
        if (email.equals(adminEmail) && password.equals(adminPassword)) {
            progressDialog.show();
            mAuth.signInAnonymously()  // Using anonymous sign-in for simplicity
                    .addOnCompleteListener(task -> {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Log.d("LoginStatus", "Anonymous login successful");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("LoginStatus", "Anonymous login failed");
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("LoginError", "Error during login: " + e.getMessage(), e);
                        Toast.makeText(LoginActivity.this, "Login error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
        }

    }
}
