package com.example.debuggingdemonsapp;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        usernameEditText = findViewById(R.id.usernameEditText);

        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(v -> registerUser());
        loginButton.setOnClickListener(v -> loginUser());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString();

        if (username.isEmpty()) {
            showMessageDialog("Username cannot be empty", null);
            return;
        }

        db.collection("users").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document =  task.getResult();
                        if (document.exists()) {
                            showMessageDialog("Username already exists", null);
                        } else {
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("username", username);
                            db.collection("users").document(username).set(userMap)
                                    .addOnSuccessListener(aVoid -> {
                                        showMessageDialog("Register Successfully", () ->
                                                new Handler(Looper.getMainLooper()).postDelayed(
                                                        this::navigateToMainActivity,
                                                        500
                                                )
                                        );
                                    })
                                    .addOnFailureListener(e -> showMessageDialog("Error: " + e.getMessage(), null));
                        }
                    } else {
                        showMessageDialog("Error checking username", null);
                    }
                });
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString();

        if (username.isEmpty()) {
            showMessageDialog("Username cannot be empty", null);
            return;
        }

        db.collection("users").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            showMessageDialog("Logged in successfully", () -> {
                                new Handler(Looper.getMainLooper()).postDelayed(this::navigateToMainActivity, 500);
                            });
                        } else {
                            showMessageDialog("Username does not exist", null);
                        }
                    } else {
                        showMessageDialog("Error logging in", null);
                    }
                });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showMessageDialog(String message, Runnable onDismiss) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    if (onDismiss != null) {
                        onDismiss.run();
                    }
                })
                .setCancelable(false)
                .show();
    }

}

