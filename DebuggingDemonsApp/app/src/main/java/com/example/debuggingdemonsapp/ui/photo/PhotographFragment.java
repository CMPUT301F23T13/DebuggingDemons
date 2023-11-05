package com.example.debuggingdemonsapp.ui.photo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentSavedBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class PhotographFragment extends Fragment {


    private FragmentSavedBinding binding;
    private SavedPhotoList photoList;
    ArrayList<ImageButton> imageButtons;



    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View  The view which displays content from the fragment_saved.xml file
     */
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSavedBinding.inflate(inflater,container,false);
        imageButtons = new ArrayList<ImageButton>();

        View root = binding.getRoot();
        photoList =  ((MainActivity) getActivity()).appPhotos;


        if(!photoList.isEmpty()){
            for (Photograph photo : photoList.getPhotos()){

                addPhotoView(binding, photo);

            }
        }
        System.out.println(imageButtons.size());
        for (ImageButton imageButton : imageButtons){
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    System.out.println(imageButtons.indexOf(imageButton));

                    findNavController(container).navigate(R.id.action_navigation_photosList_to_navigation_addItem);
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
     * Used to add ImageViews to the 'Photos' page when photos are taken with the app's camera
     * @param binding
     * @param photo
     */
    public void addPhotoView(FragmentSavedBinding binding, Photograph photo){
        ImageButton template = binding.image1;

        ImageButton imageButton = new ImageButton(getContext());
        // For some reason the view needs to be rotated for the emulator to display photos in the right orientation.
        //  This needs to be changed for real devices
        imageButton.setRotation(90);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) template.getLayoutParams();
        params.setMargins(0,100,0,0);

        imageButton.setLayoutParams(params);
        imageButton.setOutlineProvider(template.getOutlineProvider());

        imageButton.setImageBitmap(photo.photoBitmap());
        imageButton.setId(View.generateViewId());

        imageButtons.add(imageButton);
        binding.photoContainer.addView(imageButton);
    }

}
