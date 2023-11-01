package com.example.debuggingdemonsapp.ui.camera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.camera.core.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.debuggingdemonsapp.databinding.FragmentPictureBinding;

public class PhotoPreview extends Fragment {

    private FragmentPictureBinding binding;
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
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
