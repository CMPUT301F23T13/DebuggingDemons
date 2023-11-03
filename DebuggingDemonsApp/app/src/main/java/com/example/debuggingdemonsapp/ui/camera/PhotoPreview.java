package com.example.debuggingdemonsapp.ui.camera;

<<<<<<< Updated upstream
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.camera.core.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.debuggingdemonsapp.databinding.FragmentPictureBinding;
=======
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.camera.core.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.FragmentNavigator;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.ActivityMainBinding;
import com.example.debuggingdemonsapp.databinding.FragmentCameraBinding;
import com.example.debuggingdemonsapp.databinding.FragmentPictureBinding;
import com.example.debuggingdemonsapp.ui.inventory.AddInventoryFragment;
import com.example.debuggingdemonsapp.ui.photo.Photograph;
import com.google.android.material.navigation.NavigationBarView;
import org.jetbrains.annotations.NotNull;

import static android.content.Intent.getIntent;
import static androidx.navigation.Navigation.findNavController;
>>>>>>> Stashed changes

public class PhotoPreview extends Fragment {

    private FragmentPictureBinding binding;
<<<<<<< Updated upstream
    private ImageProxy newImage;

    public PhotoPreview(ImageProxy image){
        this.newImage = image;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CameraViewModel cameraViewModel =
                new ViewModelProvider(this).get(CameraViewModel.class);

        binding = FragmentPictureBinding.inflate(inflater, container, false);

        binding.imageView.setImageBitmap(newImage.toBitmap());
=======


    private Bitmap newImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPictureBinding.inflate(inflater, container, false);

        newImage = getArguments().getParcelable("Image");

        binding.imageView.setImageBitmap(newImage);

        // Calls 'enable' method from MainActivity with 'false' passed in, which makes it so that you can't press the bottom navigation icons
        ((MainActivity) getActivity()).enable(false);

        binding.retakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calls 'enable' method from MainActivity with 'true' passed in, which makes it so that you can press the bottom navigation icons
                ((MainActivity) getActivity()).enable(true);
                findNavController(container).navigate(R.id.navigation_camera);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Photograph photo = new Photograph(newImage);
                Toast.makeText(getContext(), "Photo Saved",getId());
                System.out.println(photo.getSavedPhoto());
            }
        });

>>>>>>> Stashed changes
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
