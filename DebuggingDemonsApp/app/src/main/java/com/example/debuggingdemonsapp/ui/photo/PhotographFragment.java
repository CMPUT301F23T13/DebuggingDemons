package com.example.debuggingdemonsapp.ui.photo;

import static androidx.navigation.Navigation.findNavController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.databinding.FragmentInventoryBinding;
import com.example.debuggingdemonsapp.databinding.FragmentPhotolistBinding;
import com.example.debuggingdemonsapp.model.Photograph;
import com.example.debuggingdemonsapp.model.SavedPhotoList;

import java.util.ArrayList;

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
     * @return View object returned
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

        // Loops through all the created image buttons in the imageButtons list
        for (ImageButton imageButton : imageButtons){
            imageButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // When a long press occurs, a dialog appears asking the user whether they want to delete the photo
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Delete Photo")
                            .setMessage("Would you like to delete this photo?")
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Deletes photo from app's saved photos and then deletes the imageButton from the photolist menu
                                    Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
                                    deletePhoto(bitmap);
                                    LinearLayout container = binding.photoContainer;
                                    container.removeView(imageButton);
                                    Toast.makeText(getContext(), "Photo deleted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                    return false;
                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // The getArguments method isn't null when the photolist is navigated to from the adding/editing item menus
                    if(getArguments() != null){
                        String key = "";
                        int button_id = (int) getArguments().get("button");
                        // Sets key based on value of the button id that was passed through in the bundle
                        if (button_id == R.id.addImage1 || button_id == R.id.editImage1){
                            key = "image1";
                        } else if (button_id == R.id.addImage2 || button_id == R.id.editImage2) {
                            key = "image2";
                        } else if (button_id == R.id.addImage3 || button_id == R.id.editImage3) {
                            key = "image3";
                        }

                        Photograph photoObj = photoList.getPhotos().get(imageButtons.indexOf(imageButton));
                        // Sets the MutableLiveData object corresponding to the key to the bitmap of the selected image
                        // Then returns to the add item details page
                        findNavController(container).getPreviousBackStackEntry().getSavedStateHandle().set(key,  photoObj);
                        findNavController(container).popBackStack();
                    }
                }
            });
        }

        // Used to return to the page that was navigated from when back button is pressed
        binding.photoListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              findNavController(container).popBackStack();

            }
        });

        return root;

    }

    /**
     * This method is used to delete a photo from the list of photos when it is double-clicked
     */
    public void deletePhoto(Bitmap bitmap){

        int imageIndex = ((MainActivity)getActivity()).appPhotos.itemIndexWithBitmap(bitmap);
        // Need to update database with deletion

        ((MainActivity)getActivity()).appPhotos.getPhotos().remove(imageIndex);
    }

    /**
     * Used to add ImageViews to the 'Saved Photos' page when photos are taken with the app's camera
     * @param binding FragmentPhotolistBinding object which allows items
     * @param photo Photograph object which represents the photo from the appPhotos list that the imageButton should hold
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
