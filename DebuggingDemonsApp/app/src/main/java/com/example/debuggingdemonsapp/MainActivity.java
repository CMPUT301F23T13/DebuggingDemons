package com.example.debuggingdemonsapp;

import android.os.Bundle;

import com.example.debuggingdemonsapp.model.SavedPhotoList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.debuggingdemonsapp.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    private ActivityMainBinding binding;
    public SavedPhotoList appPhotos;
    public String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        current_user = getIntent().getStringExtra("current_user");

        db = FirebaseFirestore.getInstance();

        appPhotos = new SavedPhotoList();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_inventory, R.id.navigation_camera, R.id.navigation_notifications, R.id.navigation_tag)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    /**
     * Method used to enable and disable the bottom navigation bar's items
     * @param setActive
     */
    public void enable(boolean setActive){
        for(int i = 0; i < binding.navView.getMenu().size(); i++){
            binding.navView.getMenu().getItem(i).setEnabled(setActive);
        }
    }
}