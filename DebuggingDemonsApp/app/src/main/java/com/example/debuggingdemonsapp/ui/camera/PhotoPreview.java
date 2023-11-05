package com.example.debuggingdemonsapp.ui.camera;


import android.content.Context;
import android.graphics.Bitmap;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.navigation.Navigation.findNavController;


public class PhotoPreview extends Fragment {

    private FragmentPictureBinding binding;
    private Photograph newPhoto;
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

                ((MainActivity) getActivity()).appPhotos.addPhoto(newPhoto);

//                photosRef.add(newPhoto); // Adds Photograph object's bitmap to a photos database collection
//                System.out.println(((MainActivity) getActivity()).appPhotos.getPhotos());

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
