package com.example.debuggingdemonsapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String username;
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
        username = usernameEditText.getText().toString();

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
                                        showMessageDialog("Register Successfully", this::navigateToMainActivity);
                                    })
                                    .addOnFailureListener(e -> showMessageDialog("Error: " + e.getMessage(), null));
                        }
                    } else {
                        showMessageDialog("Error checking username", null);
                    }
                });
    }

    private void loginUser() {
        username = usernameEditText.getText().toString();

        if (username.isEmpty()) {
            showMessageDialog("Username cannot be empty", null);
            return;
        }

        db.collection("users").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            showMessageDialog("Logged in successfully", this::navigateToMainActivity);
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
        intent.putExtra("current_user", username);
        startActivity(intent);
        finish();
    }

    private void showMessageDialog(String message, Runnable onDismiss) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (onDismiss != null) {
                onDismiss.run();
            }
        }, 1000); // delay one second to show the message
    }

}

