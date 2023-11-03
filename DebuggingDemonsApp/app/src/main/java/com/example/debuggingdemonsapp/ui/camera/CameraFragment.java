package com.example.debuggingdemonsapp.ui.camera;

<<<<<<< Updated upstream
=======
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
>>>>>>> Stashed changes
import androidx.camera.core.*;
import androidx.camera.core.Camera;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
<<<<<<< Updated upstream
=======

import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.ActivityMainBinding;
>>>>>>> Stashed changes
import com.example.debuggingdemonsapp.databinding.FragmentPictureBinding;
import com.google.common.util.concurrent.ListenableFuture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.debuggingdemonsapp.databinding.FragmentCameraBinding;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

<<<<<<< Updated upstream
// Used https://developer.android.com/training/camerax/preview for in-app camera
=======
import static androidx.navigation.Navigation.findNavController;

// Used https://developer.android.com/training/camerax/preview for in-app camera

/**
 * Class is used to display the in-app Camera as a fragment
 */
>>>>>>> Stashed changes
public class CameraFragment extends Fragment {

    private FragmentCameraBinding binding;
    private FragmentPictureBinding binding2;
    private PhotoPreview photoPreviewFragment;
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
        CameraViewModel cameraViewModel =
                new ViewModelProvider(this).get(CameraViewModel.class);

        binding = FragmentCameraBinding.inflate(inflater, container, false);
        binding2 = FragmentPictureBinding.inflate(inflater,container,false);
        View root = binding.getRoot();


        // Calls method that is used to start the in-app camera
        openCamera();

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Issue: When the button is pressed to many times in a short timeframe the TakePictureManager says 'There is already a request in-flight'
                //      and it refuses to take picture
                Executor cameraExector = new Executor() {
                    @Override
                    public void execute(Runnable runnable) {
                        runnable.run();
                    }
                };

                takePhoto.takePicture(cameraExector, new ImageCapture.OnImageCapturedCallback() {

                    public void onCaptureSuccess(@NonNull @NotNull ImageProxy image) {
<<<<<<< Updated upstream
//                        binding2.imageView.setRotation(90);
//                        binding2.imageView.setImageBitmap(image.toBitmap());
//                        View root2 = binding2.getRoot();
                        getFragmentManager().beginTransaction()
                                .replace(getId(),new PhotoPreview(image))
                                .commit();
//                        getActivity().setContentView(root2);
                    }
                        }
                    );
=======
                        // from https://stackoverflow.com/questions/33797036/how-to-send-the-bitmap-into-bundle
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("Image", image.toBitmap());
                        System.out.println("image");
                        ActivityMainBinding main = ActivityMainBinding.inflate(inflater,container,false);

                        findNavController(container).navigate(R.id.navigation_photo, bundle);

                    }
                }
                );
>>>>>>> Stashed changes
            }});

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
                ProcessCameraProvider cameraProvider = cameraProviderLF.get();

                // The bindPreview method is then called with the 'cameraProvider' object passed in
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {

            }
        }, ContextCompat.getMainExecutor(requireActivity()));
    }
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Used to create the new camera object by binding to the preview view in fragment_camera.xml
     * @param cameraProvider
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

        takePhoto = new ImageCapture.Builder()
                .setTargetRotation(requireView().getDisplay().getRotation())
                .build();
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        // Creates a new camera object using the cameraProvider which is bound to the 'selectCamera'
        //      and the 'preview' Preview object that was created in this method
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, selectCamera, takePhoto, preview);

    }

}