package com.example.debuggingdemonsapp.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debuggingdemonsapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddInventoryFragment extends Fragment {

    private EditText editTextDateOfPurchase;
    private EditText editTextDescription;
    private EditText editTextMake;
    private EditText editTextModel;
    private EditText editTextSerialNumber;
    private EditText editTextEstimatedValue;
    private EditText editTextComment;

    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");

        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        editTextDateOfPurchase = view.findViewById(R.id.editTextDateOfPurchase);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextMake = view.findViewById(R.id.editTextMake);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextSerialNumber = view.findViewById(R.id.editTextSerialNumber);
        editTextEstimatedValue = view.findViewById(R.id.editTextEstimatedValue);
        editTextComment = view.findViewById(R.id.editTextComment);

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

    private void addItemToDatabase() {
        String dateOfPurchase = editTextDateOfPurchase.getText().toString();
        String description = editTextDescription.getText().toString();
        String make = editTextMake.getText().toString();
        String model = editTextModel.getText().toString();
        String serialNumber = editTextSerialNumber.getText().toString();
        String estimatedValue = editTextEstimatedValue.getText().toString();
        String comment = editTextComment.getText().toString();

        Map<String, Object> newItem = new HashMap<>();
        newItem.put("dateOfPurchase", dateOfPurchase);
        newItem.put("description", description);
        newItem.put("make", make);
        newItem.put("model", model);
        newItem.put("serialNumber", serialNumber);
        newItem.put("estimatedValue", estimatedValue);
        newItem.put("comment", comment);

        itemsRef.add(newItem)
                .addOnSuccessListener(documentReference -> {
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "Item added successfully", Toast.LENGTH_SHORT).show();

                        InventoryViewModel viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
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
}
