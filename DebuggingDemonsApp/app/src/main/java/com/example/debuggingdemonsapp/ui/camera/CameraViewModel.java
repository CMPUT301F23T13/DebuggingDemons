package com.example.debuggingdemonsapp.ui.camera;

import android.graphics.Bitmap;
import androidx.camera.core.ImageProxy;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CameraViewModel extends ViewModel {

    private Bitmap bitImage;

    public CameraViewModel() {
       bitImage = null;
    }

    public Bitmap getmImage(ImageProxy imag) {
//        bitImage =
        return bitImage;
    }
}