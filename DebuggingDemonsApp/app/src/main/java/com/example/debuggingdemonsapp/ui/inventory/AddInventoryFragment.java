package com.example.debuggingdemonsapp.ui.inventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.debuggingdemonsapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddInventoryFragment extends DialogFragment {

    private EditText editTextDateOfPurchase;
    private EditText editTextDescription;
    private EditText editTextMake;
    private EditText editTextModel;
    private EditText editTextSerialNumber;
    private EditText editTextEstimatedValue;
    private EditText editTextComment;

    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("items");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_item, null);

        editTextDateOfPurchase = view.findViewById(R.id.editTextDateOfPurchase);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextMake = view.findViewById(R.id.editTextMake);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextSerialNumber = view.findViewById(R.id.editTextSerialNumber);
        editTextEstimatedValue = view.findViewById(R.id.editTextEstimatedValue);
        editTextComment = view.findViewById(R.id.editTextComment);

        builder.setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", (dialog, id) -> {
                    NavController navController = NavHostFragment.findNavController(AddInventoryFragment.this);
                    navController.navigate(R.id.navigation_inventory);
                })
                .setPositiveButton("OK", (dialog, id) -> {
                    addItemToDatabase();
                    NavController navController = NavHostFragment.findNavController(AddInventoryFragment.this);
                    navController.navigate(R.id.navigation_inventory);
                });

        return builder.create();
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
                    if (isAdded() && getActivity() != null) {
                        Toast.makeText(getActivity(), "Item added successfully", Toast.LENGTH_SHORT).show();

                        InventoryViewModel viewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);
                        viewModel.fetchItems();
                    }
                })
                .addOnFailureListener(e -> {
                    if (isAdded() && getActivity() != null) {
                        Toast.makeText(getActivity(), "Error adding item", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
