package com.example.debuggingdemonsapp.ui.camera;


import android.Manifest;
import android.content.pm.PackageManager;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultLauncherKt;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.OptIn;
import androidx.camera.core.*;
import androidx.camera.core.Camera;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.ActivityMainBinding;

import com.example.debuggingdemonsapp.databinding.FragmentCameraBinding;
import com.example.debuggingdemonsapp.databinding.FragmentPictureBinding;
import com.google.common.util.concurrent.ListenableFuture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


// Used https://developer.android.com/training/camerax/preview for in-app camera

import static androidx.navigation.Navigation.findNavController;

// Used https://developer.android.com/training/camerax/preview for in-app camera

/**
 * Class is used to display the in-app Camera's page
 */

public class CameraFragment extends Fragment {

    private FragmentCameraBinding binding;
    private ProcessCameraProvider cameraProvider;

    private ImageCapture takePhoto;
    private ListenableFuture<ProcessCameraProvider> cameraProviderLF; //LF = Listenable Future

    /**
     * Used to create the CameraFragment view and call methods needed for the in-app camera
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCameraBinding.inflate(inflater, container, false);

        View root = binding.getRoot();


        // Calls method that is used to start the in-app camera
        openCamera();

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Issue: When the button is pressed to many times in a short timeframe the TakePictureManager says 'There is already a request in-flight'
                //      and it refuses to take picture (FIXED)
                Executor cameraExector = new Executor() {
                    @Override
                    public void execute(Runnable runnable) {
                        runnable.run();
                    }
                };

                takePhoto.takePicture(cameraExector, new ImageCapture.OnImageCapturedCallback() {
                    @OptIn(markerClass = ExperimentalGetImage.class) public void onCaptureSuccess(@NonNull @NotNull ImageProxy image) {


                    // from https://stackoverflow.com/questions/33797036/how-to-send-the-bitmap-into-bundle
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Image", image.toBitmap());
                    image.close();
                    cameraProvider.unbindAll();

                    findNavController(container).navigate(R.id.navigation_photoPreview, bundle);

            }
        }
        );}});
        binding.photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraProvider.unbindAll(); //Ensures that camera unbinds so if user navigates back to camera it will create a new binding
                findNavController(container).navigate(R.id.navigation_photosList);
            }
        });

    return root;
}

/**
 * Opens the phone's camera so that it can be used by the in-app camera page
 */
private void openCamera() {
        // The ProcessCameraProvider class is used to access all the cameras that exist
        // cameraProviderLF is used to listen for any future events of the ProcessCameraProvider
        cameraProviderLF = ProcessCameraProvider.getInstance(requireActivity());

        cameraProviderLF.addListener(() -> {
        try {
        // Once an event occurs a new ProcessCameraProvider object, cameraProvider
        // cameraProvider is assigned to the return from cameraProviderLF
        cameraProvider = cameraProviderLF.get();

        // The bindPreview method is then called with the 'cameraProvider' object passed in
        bindPreview(cameraProvider);
        } catch (ExecutionException | InterruptedException e) {

        }
        }, ContextCompat.getMainExecutor(requireActivity()));
        }

@Override
public void onDestroyView() {
        super.onDestroyView();
        cameraProvider.unbindAll();
        binding = null;
}

        /**
         * Used to create the new camera object by binding to the preview view in fragment_camera.xml
         * Outstanding Issue: Keep getting SurfaceTexture dequeue buffer error. Tried using image.close() and cameraProvider.unbindall()
         *                      to fix this but didn't work. After researching, some say that error is a bug with Android
         * @param cameraProvider ProcessCameraProvider object which is used to bind camera elements to view lifecycle
         */
        void bindPreview(@NonNull ProcessCameraProvider cameraProvider){
        // The following code is from the Android Studio CameraX page + their CameraX tutorial
        // Creates a new preview object which will be connected to the 'PreviewView' that is in the fragment_camera.xml file
        Preview preview = new Preview.Builder().build();

        // Sets the mode for the 'cameraView' PreviewView object from fragment_camera.xml
        binding.cameraView.setImplementationMode(PreviewView.ImplementationMode.COMPATIBLE);
        preview.setSurfaceProvider(binding.cameraView.getSurfaceProvider());

        // Used to select the back camera as the one that is used
        CameraSelector selectCamera = CameraSelector.DEFAULT_BACK_CAMERA;

        // Necessary class builder called to allow image capturing
        takePhoto = new ImageCapture.Builder()
        .setTargetRotation(requireView().getDisplay().getRotation())
        .build();

        // Asks user for permission to use the phone's camera
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 7);
        }
        // Creates a new camera object using the cameraProvider which is bound to the 'selectCamera'
        //      and the 'preview' Preview object that was created in this method
        Camera camera = cameraProvider.bindToLifecycle(getViewLifecycleOwner(), selectCamera, takePhoto, preview);
        }

}