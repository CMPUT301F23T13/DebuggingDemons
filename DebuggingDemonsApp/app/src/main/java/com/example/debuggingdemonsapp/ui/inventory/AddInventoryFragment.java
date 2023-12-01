package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.debuggingdemonsapp.MainActivity;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.model.Photograph;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AddInventoryFragment extends Fragment {

    private EditText editTextDateOfPurchase;
    private EditText editTextDescription;
    private EditText editTextMake;
    private EditText editTextModel;
    private EditText editTextSerialNumber;
    private EditText editTextEstimatedValue;
    private EditText editTextComment;
    private ImageButton addImage1;
    private MutableLiveData<Photograph> liveData1;
    private ImageButton addImage2;
    private MutableLiveData<Photograph> liveData2;
    private ImageButton addImage3;
    private MutableLiveData<Photograph> liveData3;

    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    /**
     * This method creates the view for adding details to an item
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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("users"+"/"+((MainActivity)getActivity()).current_user + "/" + "items");

        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        editTextDateOfPurchase = view.findViewById(R.id.editTextDateOfPurchase);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextMake = view.findViewById(R.id.editTextMake);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextSerialNumber = view.findViewById(R.id.editTextSerialNumber);
        editTextEstimatedValue = view.findViewById(R.id.editTextEstimatedValue);
        editTextComment = view.findViewById(R.id.editTextComment);

        addImage1 = view.findViewById(R.id.addImage1);
        addImage2 = view.findViewById(R.id.addImage2);
        addImage3 = view.findViewById(R.id.addImage3);


        // Need to figure out how to make this process more efficient
        // Had issues with trying to generalize these as the code would result in all three boxes having the same image when one box's image was changed
        liveData1 = Navigation.findNavController(container).getCurrentBackStackEntry()
                .getSavedStateHandle().getLiveData("image1");
        liveData1.observe(getViewLifecycleOwner(), new Observer<Photograph>() {
            @Override
            public void onChanged(Photograph photo) {
                addImage1.setImageBitmap(liveData1.getValue().photoBitmap());
                addImage1.setRotation(90);

            }
        });
        chooseImage(addImage1,container);


        liveData2 = Navigation.findNavController(container).getCurrentBackStackEntry()
                .getSavedStateHandle().getLiveData("image2");
        liveData2.observe(getViewLifecycleOwner(), new Observer<Photograph>() {
            @Override
            public void onChanged(Photograph photo) {
                addImage2.setImageBitmap(liveData2.getValue().photoBitmap());
                addImage2.setRotation(90);

            }
        });
        chooseImage(addImage2,container);

        liveData3 = Navigation.findNavController(container).getCurrentBackStackEntry()
                .getSavedStateHandle().getLiveData("image3");
        liveData3.observe(getViewLifecycleOwner(), new Observer<Photograph>() {
            @Override
            public void onChanged(Photograph photo) {
                addImage3.setImageBitmap(liveData3.getValue().photoBitmap());
                addImage3.setRotation(90);

            }
        });
        chooseImage(addImage3,container);


        view.findViewById(R.id.button_cancel).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_inventory);
        });

        view.findViewById(R.id.button_ok).setOnClickListener(v -> {
            addItemToDatabase();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_inventory);
        });

        return view;
    }

    /**
     * This method deals with adding an item to the database
     */
    private void addItemToDatabase() {
        String dateOfPurchase = editTextDateOfPurchase.getText().toString();
        String description = editTextDescription.getText().toString();
        String make = editTextMake.getText().toString();
        String model = editTextModel.getText().toString();
        String serialNumber = editTextSerialNumber.getText().toString();
        String estimatedValue = editTextEstimatedValue.getText().toString();
        String comment = editTextComment.getText().toString();

        // Strings which will correspond to the images' URI set as "" initially to ensure that if no image is chosen
        //   for a specific imagebutton, it will just have an empty string when added to database
        String image1 = "";
        String image2 = "";
        String image3 = "";

        // Gets the storageURI of an image when an image has been added to the ImageButton
        if (liveData1.getValue() != null){
            image1 = liveData1.getValue().storageURI();
        }
        if(liveData2.getValue() != null){
            image2 = liveData2.getValue().storageURI();
        }
        if(liveData3.getValue() != null){
            image3 = liveData3.getValue().storageURI();
        }


        Map<String, Object> newItem = new HashMap<>();
        newItem.put("dateOfPurchase", dateOfPurchase);
        newItem.put("description", description);
        newItem.put("make", make);
        newItem.put("model", model);
        newItem.put("serialNumber", serialNumber);
        newItem.put("estimatedValue", estimatedValue);
        newItem.put("comment", comment);
        newItem.put("image1", image1);
        newItem.put("image2",image2);
        newItem.put("image3",image3);

        itemsRef.add(newItem)
                .addOnSuccessListener(documentReference -> {
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "Item added successfully", Toast.LENGTH_SHORT).show();

//                        InventoryViewModel viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
                        InventoryViewModel viewModel = new ViewModelProvider(requireActivity(),new InventoryViewModelFactory(((MainActivity)getActivity()).current_user)).get(InventoryViewModel.class);

                        viewModel.fetchItems();

                        NavController navController = Navigation.findNavController(getView());
                        navController.navigate(R.id.navigation_inventory);
                    }
                })
                .addOnFailureListener(e -> {
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "Error adding item", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * This method is used to navigate to the saved images list when an ImageButton is pressed. Allows user to choose an image
     * @param imageButton This corresponds to an ImageButton object
     * @param container  The container that is created in the onCreateView method
     */

    public void chooseImage(ImageButton imageButton, ViewGroup container){
        // Called to listen for a click on the imageButton that is passed as an argument

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creates bundle that holds the id of the button that was clicked
                //  Then navigates to page with list of saved photos
                Bundle bundle = new Bundle();
                bundle.putInt("button", imageButton.getId());
                Navigation.findNavController(requireView()).navigate(R.id.navigation_photosList,bundle);

            }
        });

    }
}