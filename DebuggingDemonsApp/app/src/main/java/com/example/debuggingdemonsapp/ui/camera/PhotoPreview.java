package com.example.debuggingdemonsapp.ui.camera;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentPictureBinding;
import com.example.debuggingdemonsapp.ui.photo.Photograph;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import static androidx.navigation.Navigation.findNavController;


public class PhotoPreview extends Fragment {

    private FragmentPictureBinding binding;
    private Photograph newPhoto;
    private FirebaseStorage storage;

    private Bitmap newImage;


    /**
     *  This method is used to create the fragment view which shows a preview of the photo taken.
     *      Also allows user to choose whether they want to retake the photo or save it.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View The view object that is used to display the fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPictureBinding.inflate(inflater, container, false);
        newImage = getArguments().getParcelable("Image");
        newPhoto = new Photograph(newImage);

        binding.imageView.setImageBitmap(newImage);

        storage = FirebaseStorage.getInstance("gs://debuggingdemons.appspot.com/");


//        StorageReference imagesRef = storageRef.child("images");

        // Calls 'enable' method from MainActivity with 'false' passed in, which makes it so that you can't press the bottom navigation icons
        ((MainActivity) getActivity()).enable(false);

        binding.retakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calls 'enable' method from MainActivity with 'true' passed in, which makes it so that you can press the bottom navigation icons
                backToCamera(container);
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Photo Saved", getId()).show();

                // Adding images to the Firestore Storage from https://firebase.google.com/docs/storage/web/upload-files

                StorageReference storageRef = storage.getReference("image"+((MainActivity) getActivity()).appPhotos.getPhotos().size()+".jpg");
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

                // Needed to rotate image for it to be in the right orientation in the database
                // Need to figure out how to have image orientation based on camera/phone orientation
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                Bitmap rotatednewPhoto = Bitmap.createBitmap(newPhoto.photoBitmap(),0,0, newPhoto.photoBitmap().getWidth(),newPhoto.photoBitmap().getHeight(),matrix,true);

                rotatednewPhoto.compress(Bitmap.CompressFormat.JPEG, 100, byteOutput);
                byte[] imageData = byteOutput.toByteArray();

                storageRef.putBytes(imageData).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        System.out.println(e);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        System.out.println(taskSnapshot);
                    }
                });
                ((MainActivity) getActivity()).appPhotos.addPhoto(newPhoto);


                backToCamera(container);
            }
        });


        View root = binding.getRoot();

        return root;
    }


        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }

    /**
     * Navigates back to the Camera screen which is used to take photos
     * @param container The container object is from onCreateView and is used for navigation
     */
    public void backToCamera(ViewGroup container){
            ((MainActivity) getActivity()).enable(true);
            findNavController(container).navigate(R.id.navigation_camera);
        }

}
