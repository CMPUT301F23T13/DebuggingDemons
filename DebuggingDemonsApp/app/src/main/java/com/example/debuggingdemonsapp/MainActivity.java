package com.example.debuggingdemonsapp;

import android.os.Bundle;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.debuggingdemonsapp.databinding.ActivityMainBinding;
<<<<<<< Updated upstream
=======
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;

>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
<<<<<<< Updated upstream


=======
        getSupportActionBar().hide();
>>>>>>> Stashed changes
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_camera, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

<<<<<<< Updated upstream
=======
    /**
     * Method used to enable and disable the bottom navigation bar's items
     * @param setActive
     */
    public void enable(boolean setActive){
        for(int i = 0; i < binding.navView.getMenu().size(); i++){
            binding.navView.getMenu().getItem(i).setEnabled(setActive);
        }
    }

>>>>>>> Stashed changes
}