package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.debuggingdemonsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

/**
 * Fragment for editing item details.
 */
public class EditItemFragment extends Fragment {
    private EditText DoP;
    private EditText Description;
    private EditText Make;
    private EditText Model;
    private EditText SerialNumber;
    private EditText EstimatedValue;
    private EditText Comment;
    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    /**
     * Default constructor for EditItemFragment.
     */
    public EditItemFragment() {

    }
    /**
     * Creates and returns the view associated with the fragment.
     *
     * @param inflater           LayoutInflater that can be used to inflate views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment will be re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Initialize Database and collection reference
        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");
    // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_edit, container, false);
        DoP = view.findViewById(R.id.dateOfPurchase);
        Description = view.findViewById(R.id.description);
        Make = view.findViewById(R.id.make);
        Model = view.findViewById(R.id.model);
        SerialNumber = view.findViewById(R.id.serial_number);
        EstimatedValue = view.findViewById(R.id.estimated_value);
        Comment = view.findViewById(R.id.comment);

        Button confirmButton = view.findViewById(R.id.confirm_button);
        Button cancelButton = view.findViewById(R.id.back_button);

        // Retrieve data from bundle if not null and initialize the UI
        Bundle bundle = getArguments();
        if (bundle != null) {
            System.out.println(bundle.getString("doP"));
            String doP = bundle.getString("doP");
            String description = bundle.getString("description");
            String make = bundle.getString("make");
            String model = bundle.getString("model");
            String serialNumber = bundle.getString("serialNumber");
            String estimatedValue = bundle.getString("estimatedValue");
            String comment = bundle.getString("comment");

            DoP.setText(doP);
            Description.setText(description);
            Make.setText(make);
            Model.setText(model);
            SerialNumber.setText(serialNumber);
            EstimatedValue.setText(estimatedValue);
            Comment.setText(comment);
        }


        // Set click listeners for confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                // Fetch updated items from ViewModel
                InventoryViewModel viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
                viewModel.fetchItems();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the inventory fragment
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.navigation_inventory);
            }
        });
        return view;
    }

    /**
     * Save edited item data to Database.
     */
    private void saveData(){
        // Retrieve updated data from UI elements
        String updatedDoP = DoP.getText().toString();
        String updatedDescription = Description.getText().toString();
        String updatedMake = Make.getText().toString();
        String updatedModel = Model.getText().toString();
        String updatedSerialNumber = SerialNumber.getText().toString();
        String updatedEstimatedValue = EstimatedValue.getText().toString();
        String updatedComment = Comment.getText().toString();

        // Query data for the item with the specified description
        Query query = itemsRef.whereEqualTo("description", updatedDescription);

        // Perform the query and update the item details
        query.get(Source.SERVER).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Update item details in Database
                        document.getReference().update("dateOfPurchase", updatedDoP,
                                        "make", updatedMake,
                                        "model", updatedModel,
                                        "serialNumber", updatedSerialNumber,
                                        "estimatedValue", updatedEstimatedValue,
                                        "comment", updatedComment)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // Show a Toast message based on the update result
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Update successfully!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "Update failed, please try again later.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
}
