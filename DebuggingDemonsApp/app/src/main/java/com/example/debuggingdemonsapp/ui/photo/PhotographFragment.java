package com.example.debuggingdemonsapp.ui.photo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.example.debuggingdemonsapp.databinding.FragmentPhotolistBinding;
import com.example.debuggingdemonsapp.model.Photograph;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

/**
 * Class creates a fragment which displays all the photos that the user saved in the app
 */
public class PhotographFragment extends Fragment {


    private FragmentPhotolistBinding binding;
    private FragmentInventoryBinding inventoryBinding;
    private SavedPhotoList photoList;
    ArrayList<ImageButton> imageButtons;

    /**
     * Used to create the PhotographFragment which displays the list of saved photos
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return
     */


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPhotolistBinding.inflate(inflater,container,false);

        imageButtons = new ArrayList<>();

        View root = binding.getRoot();
        photoList =  ((MainActivity) getActivity()).appPhotos;


        if(!photoList.isEmpty()){
            for (Photograph photo : photoList.getPhotos()){

                addPhotoView(binding, photo);

            }
        }

        for (ImageButton imageButton : imageButtons){
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String key = null;
                    int button_id = (int) getArguments().get("button");
                    // Sets key based on value of the button id that was passed through in the bundle
                    if (button_id == R.id.addImage1){
                        key = "image1";
                    } else if (button_id == R.id.addImage2) {
                        key = "image2";
                    } else if (button_id == R.id.addImage3) {
                        key = "image3";
                    }

                    Photograph photoObj = photoList.getPhotos().get(imageButtons.indexOf(imageButton));
                    // Sets the MutableLiveData object corresponding to the key to the bitmap of the selected image
                    // Then returns to the add item details page
                    findNavController(container).getPreviousBackStackEntry().getSavedStateHandle().set(key,  photoObj);
                    findNavController(container).popBackStack();
                }
            });
        }


        binding.photoListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              findNavController(container).popBackStack();

            }
        });

        return root;

    }


    /**
     * Used to add ImageViews to the 'Saved Photos' page when photos are taken with the app's camera
     * @param binding
     * @param photo
     */
    public void addPhotoView(FragmentPhotolistBinding binding, Photograph photo){

        ImageButton imageButton = new ImageButton(getContext());

        // For some reason the view needs to be rotated for the emulator to display photos in the right orientation.
        // Need to figure out how to set this rotation based on the phone's orientation rather than hardcoding a value
        imageButton.setRotation(90);

        imageButton.setImageBitmap(photo.photoBitmap());
        imageButton.setId(View.generateViewId());


        imageButtons.add(imageButton);
        binding.photoContainer.addView(imageButton);
    }

}
