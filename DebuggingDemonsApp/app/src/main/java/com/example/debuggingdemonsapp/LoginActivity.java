package com.example.debuggingdemonsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("username", username);
                            db.collection("users").document(username).set(userMap)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                        navigateToMainActivity();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(this, "Error checking username", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users").document(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

