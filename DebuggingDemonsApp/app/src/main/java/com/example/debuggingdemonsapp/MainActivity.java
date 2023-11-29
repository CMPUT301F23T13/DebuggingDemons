package com.example.debuggingdemonsapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.debuggingdemonsapp.model.Photograph;
import com.example.debuggingdemonsapp.model.SavedPhotoList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.debuggingdemonsapp.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import org.jetbrains.annotations.Async;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * This class is used to display the app bar, the navigation windows, and initialize the appPhotos and get the current user from the LoginActivity
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;
    private ActivityMainBinding binding;
    public SavedPhotoList appPhotos;
    public String current_user;

    /**
     * This class is used to create the navigation between fragments and objects needed during the app's lifetime
     *
     * @param savedInstanceState Bundle with the savedInstanceState in it
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        current_user = getIntent().getStringExtra("current_user");

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        appPhotos = new SavedPhotoList();
        getDBPhotos(firebaseStorage);
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
     *
     * @param setActive Boolean that corresponds to whether the item bar should be clickable or not
     */
    public void enable(boolean setActive) {
        for (int i = 0; i < binding.navView.getMenu().size(); i++) {
            binding.navView.getMenu().getItem(i).setEnabled(setActive);
        }
    }

    /**
     * This method is used to retrieve the current user's stored photos from the storage database
     * @param firebaseStorage FirebaseStorage object which references the Firebase Storage for this application
     */
    public void getDBPhotos(FirebaseStorage firebaseStorage) {
        // List to hold the indices of items from the firebase storage
        //  Used to ensure that photos are added to the photo list in the correct order
        ArrayList<Integer> indices = new ArrayList<>();
        if (current_user == null){
            current_user = "newuser";
        }
        StorageReference storageReference = firebaseStorage.getReference(current_user);
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if (!listResult.getItems().isEmpty()) {
                    for (StorageReference item : listResult.getItems()) {

                        try{
                            item.getDownloadUrl().addOnSuccessListener(uri -> {

                                Thread thread = new Thread(() -> {
                                    try {
                                        URL url = new URL(uri.toString());
                                        // Code for getting Bitmap from uri from https://stackoverflow.com/questions/38325613/bitmap-from-url-android
                                        Bitmap imageBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                                        // As we had to rotate the image 90 degrees when uploading it to the storage database,
                                        //   we have to rotate it -90 degrees to get the correct orientation for the app
                                        Matrix matrix = new Matrix();
                                        matrix.postRotate(-90);

                                        Bitmap rotatedImage = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);

                                        // A new Photograph is created with the rotated bitmap so that it can be added to the in-app photos
                                        Photograph photo = new Photograph(rotatedImage);
                                        photo.setUri(uri.toString());

                                        // Photo is added at a position based on the indices of items from the database when they are sorted from most recent to oldest
                                        if (indices.isEmpty()){
                                            appPhotos.appendPhoto(photo);
                                            indices.add(listResult.getItems().indexOf(item));

                                        }else{
                                            indices.add(listResult.getItems().indexOf(item));
                                            indices.sort(Collections.reverseOrder());
                                            appPhotos.addInPosition(indices.indexOf(listResult.getItems().indexOf(item)),photo);

                                        }

                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                                thread.start();

                            });
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Error occurred while retrieving photos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


    }

}
