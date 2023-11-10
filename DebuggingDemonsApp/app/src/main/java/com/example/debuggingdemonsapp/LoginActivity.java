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

/**
 * Activity for handling user login and registration.
 * <p>
 * This activity provides UI for users to either log in or register. It interacts with
 * FirebaseFirestore to store and validate user credentials.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String username;
    private EditText usernameEditText;

    /**
     * Initializes the activity.
     * <p>
     * This method sets up the layout for the activity, initializes Firestore database instance,
     * and sets up click listeners for the login and register buttons.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
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

    /**
     * Handles the user registration process.
     * <p>
     * This method checks if the entered username is not empty and unique. If it is unique,
     * it registers the user in the Firestore database and navigates to the main activity.
     * Otherwise, it shows an appropriate error message.
     */
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
                                    .addOnSuccessListener(aVoid -> showMessageDialog("Register Successfully", this::navigateToMainActivity))
                                    .addOnFailureListener(e -> showMessageDialog("Error: " + e.getMessage(), null));
                        }
                    } else {
                        showMessageDialog("Error checking username", null);
                    }
                });
    }

    /**
     * Handles the user login process.
     * <p>
     * This method checks if the entered username is not empty and exists in the Firestore database.
     * If the user exists, it logs in the user and navigates to the main activity. Otherwise,
     * it shows an appropriate error message.
     */
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

    /**
     * Navigates to the MainActivity.
     * <p>
     * This method starts the MainActivity and passes the current username as an extra in the intent.
     */
    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("current_user", username);
        startActivity(intent);
        finish();
    }

    /**
     * Displays a message dialog to the user.
     * <p>
     * This method shows an AlertDialog with the provided message. If a non-null Runnable is provided,
     * it is executed when the dialog is dismissed.
     *
     * @param message   The message to be displayed in the dialog.
     * @param onDismiss The Runnable to be executed when the dialog is dismissed, can be null.
     */
    private void showMessageDialog(String message, Runnable onDismiss) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isFinishing() && dialog.isShowing()) {
                dialog.dismiss();
                if (onDismiss != null) {
                    onDismiss.run();
                }
            }
        }, 1000); // delay one second to show the message
    }

}

