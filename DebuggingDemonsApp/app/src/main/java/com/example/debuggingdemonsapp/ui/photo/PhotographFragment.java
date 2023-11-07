package com.example.debuggingdemonsapp.ui.photo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.example.debuggingdemonsapp.databinding.FragmentSavedBinding;
import com.example.debuggingdemonsapp.ui.inventory.AddInventoryFragment;
import com.example.debuggingdemonsapp.ui.inventory.InventoryFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

/**
 * Class creates a fragment which displays all the photos that the user saved in the app
 */
public class PhotographFragment extends Fragment {


    private FragmentSavedBinding binding;
    private FragmentInventoryBinding inventoryBinding;
    private SavedPhotoList photoList;
    ArrayList<ImageButton> imageButtons;

    /**
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return
     */


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSavedBinding.inflate(inflater,container,false);

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

                    Toast.makeText(getContext(),"Photo selected", Toast.LENGTH_LONG).show();
//                  findNavController(container).navigate(R.id.navigation_photosList);
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
